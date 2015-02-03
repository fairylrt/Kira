import util.Random

class Extractor{
  /**Macro definitions from sep.h*/
  val SEP_TBYTE :Int = 11
  val SEP_TINT :Int = 31
  val SEP_TFLOAT :Int = 42
  val SEP_TDOUBLE :Int = 82

  val SUPPORTED_IMAGES_DTYPES = Array[Int](SEP_TDOUBLE, SEP_TFLOAT, SEP_TINT)

  /**Input flag values*/
  val SEP_ERROR_IS_VAR :Int = 0x0001
  val SEP_ERROR_IS_ARRAY :Int = 0x0002
  val SEP_MAST_IGNORE :Int = 0x0004

  /**Output flag values accesible from Scala*/
  val OBJ_MERGED :Short = 0x0001
  val OBJ_TRUNC :Short = 0x0002
  val OBJ_DOVERFLOW :Short = 0x0004
  val OBJ_SINGU :Short = 0x0008
  val APER_TRUNC :Short = 0x0010
  val APER_HASMASKED :Short = 0x0020
  val APER_ALLMASKED :Short = 0x0040
  val APER_NONPOSITIVE :Short = 0x0080

  /**Macro definition from sepcore.h*/
  val MEMORY_ALLOC_ERROR :Int = 1 
  System.loadLibrary("BackgroundImpl")

  @native
  def sep_sum_circle(data:Array[Byte], err:Array[Byte], mask:Array[Byte],
    dtype:Int, edtype:Int, mdtype:Int, w:Int, h:Int,
    maskthresh:Double, gain:Double, inflags:Short,
    x:Array[Double], y:Array[Double], r:Double, subpix:Int, sum:Array[Double],
    sumerr:Array[Double], area:Array[Double], flag:Array[Short]):Int

  @native
  def sep_sum_circann(data:Array[Byte], err:Array[Byte], mask:Array[Byte],
    dtype:Int, edtype:Int, mdtype:Int, w:Int, h:Int,
    maskthresh:Double, gain:Double, inflags:Short,
    x:Array[Double], y:Array[Double], rin:Double, rout:Double, subpix:Int, sum:Array[Double],
    sumerr:Array[Double], area:Array[Double], flag:Array[Short]):Int

  @native
  def sep_sum_ellipse(data:Array[Byte], err:Array[Byte], mask:Array[Byte],
    dtype:Int, edtype:Int, mdtype:Int, w:Int, h:Int,
    maskthresh:Double, gain:Double, inflags:Short,
    x:Array[Double], y:Array[Double], a:Array[Double], b:Array[Double], 
    theta:Array[Double], r:Double, subpix:Int, sum:Array[Double],
    sumerr:Array[Double], area:Array[Double], flag:Array[Short]):Int

  @native
  def sep_sum_ellipann(data:Array[Byte], err:Array[Byte], mask:Array[Byte],
    dtype:Int, edtype:Int, mdtype:Int, w:Int, h:Int,
    maskthresh:Double, gain:Double, inflags:Short,
    x:Array[Double], y:Array[Double], a:Array[Double], b:Array[Double], 
    theta:Array[Double], rin:Double, rout:Double, subpix:Int, sum:Array[Double],
    sumerr:Array[Double], area:Array[Double], flag:Array[Short]):Int

  def sum_circle(matrix:Array[Array[Double]], x:Array[Double], y:Array[Double], r:Double, 
    variance:Array[Array[Double]]=null, err:Array[Array[Double]]=null, gain:Double=0.0, mask:Array[Array[Double]]=null, 
    maskthresh:Double=0.0, bkgann:Array[Double]=null, subpix:Int=5):(Array[Double], Array[Double], Array[Short]) = {
    val dtype = SEP_TDOUBLE
    val edtype = if (err==null) 0 else SEP_TDOUBLE;
    val mdtype = if (err==null) 0 else SEP_TBYTE;
    val h = matrix.length
    val w = matrix(0).length
    val ptr = Utils.flatten(matrix)
    val eptr = if (err==null) null else Utils.flatten(err)
    val mptr = if (mask==null) null else Utils.flatten(mask)
    /**WARNING, need to implement the _parse_arrays function in the python implementation, this is a temporary solution here*/
    var inflag:Short = 0

    var status = 0
    var sum = Array.ofDim[Double](x.length)
    var sumerr = Array.ofDim[Double](x.length)
    var area = Array.ofDim[Double](x.length)
    var flag = Array.ofDim[Short](x.length)
    if(bkgann == null){
      status = sep_sum_circle(ptr, eptr, mptr, dtype, edtype, mdtype, w, h, maskthresh, gain, inflag, x, y, r, 
        subpix, sum, sumerr, area, flag)
    }
    else{
      var flux1 = Array.ofDim[Double](x.length)
      var fluxerr1 = Array.ofDim[Double](x.length)
      var area1 = Array.ofDim[Double](x.length)
      var flag1 = Array.ofDim[Short](x.length)
      status = sep_sum_circle(ptr, eptr, mptr, dtype, edtype, mdtype, w, h, maskthresh, gain, inflag, x, y, r, 
        subpix, flux1, fluxerr1, area1, flag1)

      var bkgflux = Array.ofDim[Double](x.length)
      var bkgfluxerr = Array.ofDim[Double](x.length)
      var bkgarea = Array.ofDim[Double](x.length)
      var bkgflag = Array.ofDim[Short](x.length)
      val rin = bkgann(0)
      val rout = bkgann(1)
      inflag = (inflag | SEP_MAST_IGNORE).toShort
      status = sep_sum_circann(ptr, eptr, mptr, dtype, edtype, mdtype, w, h, maskthresh, gain, inflag, x, y, 
        rin, rout, subpix, bkgflux, bkgfluxerr, bkgarea, bkgflag) 

      for(i <- (0 until x.length)){
        sum(i) = flux1(i) - bkgflux(i)/bkgarea(i)*area1(i);
        sumerr(i) = fluxerr1(i)*fluxerr1(i)+(bkgfluxerr(i)/bkgarea(i)*area1(i))
        flag(i) = flag1(i)
      }
    }
    println("Scala: sum_circle: return value: "+status)
    return (sum, sumerr, flag)
  }

  def sum_circann(matrix:Array[Array[Double]], x:Array[Double], y:Array[Double], rin:Double, rout:Double,  
    variance:Array[Array[Double]]=null, err:Array[Array[Double]]=null, gain:Double=0.0, mask:Array[Array[Double]]=null, 
    maskthresh:Double=0.0, bkgann:Array[Double]=null, subpix:Int=5):(Array[Double], Array[Double], Array[Short]) = {
    val dtype = SEP_TDOUBLE
    val edtype = if (err==null) 0 else SEP_TDOUBLE;
    val mdtype = if (err==null) 0 else SEP_TBYTE;
    val h = matrix.length
    val w = matrix(0).length
    val ptr = Utils.flatten(matrix)
    val eptr = if (err==null) null else Utils.flatten(err)
    val mptr = if (mask==null) null else Utils.flatten(mask)
    /**WARNING, need to implement the _parse_arrays function in the python implementation, this is a temporary solution here*/
    val inflag:Short = 0

    var sum = Array.ofDim[Double](x.length)
    var sumerr = Array.ofDim[Double](x.length)
    var area = Array.ofDim[Double](x.length)
    var flag = Array.ofDim[Short](x.length)

    val status = sep_sum_circann(ptr, eptr, mptr, dtype, edtype, mdtype, w, h, maskthresh, gain,
      inflag, x, y, rin, rout, subpix, sum, sumerr, area, flag)

    return (sum, sumerr, flag)
  }

  def sum_ellipse(matrix:Array[Array[Double]], x:Array[Double], y:Array[Double], a:Array[Double], b:Array[Double], 
    theta:Array[Double], r:Double, 
    variance:Array[Array[Double]]=null, err:Array[Array[Double]]=null, gain:Double=0.0, mask:Array[Array[Double]]=null, 
    maskthresh:Double=0.0, bkgann:Array[Double]=null, subpix:Int=5):(Array[Double], Array[Double], Array[Short]) = {
    val dtype = SEP_TDOUBLE
    val edtype = if (err==null) 0 else SEP_TDOUBLE;
    val mdtype = if (err==null) 0 else SEP_TBYTE;
    val h = matrix.length
    val w = matrix(0).length
    val ptr = Utils.flatten(matrix)
    val eptr = if (err==null) null else Utils.flatten(err)
    val mptr = if (mask==null) null else Utils.flatten(mask)
    /**WARNING, need to implement the _parse_arrays function in the python implementation, this is a temporary solution here*/
    var inflag:Short = 0

    var sum = Array.ofDim[Double](x.length)
    var sumerr = Array.ofDim[Double](x.length)
    var area = Array.ofDim[Double](x.length)
    var flag = Array.ofDim[Short](x.length)

    var status = 0
    if(bkgann == null){
      status = sep_sum_ellipse(ptr, eptr, mptr, dtype, edtype, mdtype, w, h, maskthresh, gain, inflag, x, y, 
        a, b, theta, r, subpix, sum, sumerr, area, flag)
    }
    else{
      var flux1 = Array.ofDim[Double](x.length)
      var fluxerr1 = Array.ofDim[Double](x.length)
      var area1 = Array.ofDim[Double](x.length)
      var flag1 = Array.ofDim[Short](x.length)
      status = sep_sum_ellipse(ptr, eptr, mptr, dtype, edtype, mdtype, w, h, maskthresh, gain, inflag, x, y, 
        a, b, theta, r, subpix, flux1, fluxerr1, area1, flag1)

      var bkgflux = Array.ofDim[Double](x.length)
      var bkgfluxerr = Array.ofDim[Double](x.length)
      var bkgarea = Array.ofDim[Double](x.length)
      var bkgflag = Array.ofDim[Short](x.length)
      val rin = bkgann(0)
      val rout = bkgann(1)
      inflag = (inflag | SEP_MAST_IGNORE).toShort
      status = sep_sum_ellipann(ptr, eptr, mptr, dtype, edtype, mdtype, w, h, maskthresh, gain, inflag, x, y, 
        a, b, theta, rin, rout, subpix, bkgflux, bkgfluxerr, bkgarea, bkgflag) 

      for(i <- (0 until x.length)){
        sum(i) = flux1(i) - bkgflux(i)/bkgarea(i)*area1(i);
        sumerr(i) = fluxerr1(i)*fluxerr1(i)+(bkgfluxerr(i)/bkgarea(i)*area1(i))
        flag(i) = flag1(i)
      }
    }
    return (sum, sumerr, flag)
  }

  def sum_ellipann(matrix:Array[Array[Double]], x:Array[Double], y:Array[Double], a:Array[Double], b:Array[Double], 
    theta:Array[Double], rin:Double, rout:Double, 
    variance:Array[Array[Double]]=null, err:Array[Array[Double]]=null, gain:Double=0.0, mask:Array[Array[Double]]=null, 
    maskthresh:Double=0.0, bkgann:Array[Double]=null, subpix:Int=5):(Array[Double], Array[Double], Array[Short]) = {
    val dtype = SEP_TDOUBLE
    val edtype = if (err==null) 0 else SEP_TDOUBLE;
    val mdtype = if (err==null) 0 else SEP_TBYTE;
    val h = matrix.length
    val w = matrix(0).length
    val ptr = Utils.flatten(matrix)
    val eptr = if (err==null) null else Utils.flatten(err)
    val mptr = if (mask==null) null else Utils.flatten(mask)
    /**WARNING, need to implement the _parse_arrays function in the python implementation, this is a temporary solution here*/
    var inflag:Short = 0

    var sum = Array.ofDim[Double](x.length)
    var sumerr = Array.ofDim[Double](x.length)
    var area = Array.ofDim[Double](x.length)
    var flag = Array.ofDim[Short](x.length)

    var status = 0
    if(bkgann == null){
      status = sep_sum_ellipann(ptr, eptr, mptr, dtype, edtype, mdtype, w, h, maskthresh, gain, inflag, x, y, 
        a, b, theta, rin, rout, subpix, sum, sumerr, area, flag)
    }
    return (sum, sumerr, flag)
  }

//object Test{
  def main(args: Array[String]){
    val naper = 1000
    val x = Array.fill(naper)(Random.nextDouble*(800-200)+200.0)
    val y = Array.fill(naper)(Random.nextDouble*(800-200)+200.0)
    val r = 3.0
    val matrix = Array.fill[Double](naper, naper){1.0}
    val mask = Array.fill[Boolean](naper, naper){false}
    var ex = new Extractor
    val (sum, sumerr, flag) = ex.sum_circle(matrix, x, y, r)
    println(sum.mkString(", "))
  }
}