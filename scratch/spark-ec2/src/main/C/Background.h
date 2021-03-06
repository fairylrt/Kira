/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class Background */

#ifndef _Included_Background
#define _Included_Background
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     Background
 * Method:    sep_makeback
 * Signature: ([B[[ZIIIIIIDIIDLBackground/Sepbackmap;)I
 */
JNIEXPORT jint JNICALL Java_Background_sep_1makeback
  (JNIEnv *, jobject, jbyteArray, jobjectArray, jint, jint, jint, jint, jint, jint, jdouble, jint, jint, jdouble, jobject);

/*
 * Class:     Background
 * Method:    sep_backarray
 * Signature: (LBackground/Sepbackmap;[BI[F[F[F[F)I
 */
JNIEXPORT jint JNICALL Java_Background_sep_1backarray
  (JNIEnv *, jobject, jobject, jbyteArray, jint, jfloatArray, jfloatArray, jfloatArray, jfloatArray);

/*
 * Class:     Background
 * Method:    sep_backrmsarray
 * Signature: (LBackground/Sepbackmap;[Ljava/lang/Object;I)I
 */
JNIEXPORT jint JNICALL Java_Background_sep_1backrmsarray
  (JNIEnv *, jobject, jobject, jobjectArray, jint);

/*
 * Class:     Background
 * Method:    sep_subbackarray
 * Signature: (LBackground/Sepbackmap;[BI[F[F[F[F)I
 */
JNIEXPORT jint JNICALL Java_Background_sep_1subbackarray
  (JNIEnv *, jobject, jobject, jbyteArray, jint, jfloatArray, jfloatArray, jfloatArray, jfloatArray);

/*
 * Class:     Background
 * Method:    sep_freeback
 * Signature: (LBackground/Sepbackmap;)V
 */
JNIEXPORT void JNICALL Java_Background_sep_1freeback
  (JNIEnv *, jobject, jobject);

/*
 * Class:     Background
 * Method:    sep_extract
 * Signature: ([B[BIISIIFI[BIIIDZD[LBackground/Sepobj;I)I
 */
JNIEXPORT jint JNICALL Java_Background_sep_1extract
  (JNIEnv *, jobject, jbyteArray, jbyteArray, jint, jint, jshort, jint, jint, jfloat, jint, jbyteArray, jint, jint, jint, jdouble, jboolean, jdouble, jobjectArray, jint);

/*
 * Class:     Background
 * Method:    sep_freeobjarray
 * Signature: ([LBackground/Sepobj;I)V
 */
JNIEXPORT void JNICALL Java_Background_sep_1freeobjarray
  (JNIEnv *, jobject, jobjectArray, jint);

/*
 * Class:     Background
 * Method:    sep_sum_circle
 * Signature: ([B[B[BIIIIIDDS[D[DDI[D[D[D[S)I
 */
JNIEXPORT jint JNICALL Java_Background_sep_1sum_1circle
  (JNIEnv *, jobject, jbyteArray, jbyteArray, jbyteArray, jint, jint, jint, jint, jint, jdouble, jdouble, jshort, jdoubleArray, jdoubleArray, jdouble, jint, jdoubleArray, jdoubleArray, jdoubleArray, jshortArray);

/*
 * Class:     Background
 * Method:    sep_sum_circann
 * Signature: ([B[B[BIIIIIDDS[D[DDDI[D[D[D[S)I
 */
JNIEXPORT jint JNICALL Java_Background_sep_1sum_1circann
  (JNIEnv *, jobject, jbyteArray, jbyteArray, jbyteArray, jint, jint, jint, jint, jint, jdouble, jdouble, jshort, jdoubleArray, jdoubleArray, jdouble, jdouble, jint, jdoubleArray, jdoubleArray, jdoubleArray, jshortArray);

/*
 * Class:     Background
 * Method:    sep_sum_ellipse
 * Signature: ([B[B[BIIIIIDDS[D[D[D[D[DDI[D[D[D[S)I
 */
JNIEXPORT jint JNICALL Java_Background_sep_1sum_1ellipse
  (JNIEnv *, jobject, jbyteArray, jbyteArray, jbyteArray, jint, jint, jint, jint, jint, jdouble, jdouble, jshort, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray, jdouble, jint, jdoubleArray, jdoubleArray, jdoubleArray, jshortArray);

/*
 * Class:     Background
 * Method:    sep_sum_ellipann
 * Signature: ([B[B[BIIIIIDDS[D[D[D[D[DDDI[D[D[D[S)I
 */
JNIEXPORT jint JNICALL Java_Background_sep_1sum_1ellipann
  (JNIEnv *, jobject, jbyteArray, jbyteArray, jbyteArray, jint, jint, jint, jint, jint, jdouble, jdouble, jshort, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray, jdouble, jdouble, jint, jdoubleArray, jdoubleArray, jdoubleArray, jshortArray);

/*
 * Class:     Background
 * Method:    sep_kron_radius
 * Signature: ([B[BIIIID[D[D[D[D[D[D[D[S)V
 */
JNIEXPORT void JNICALL Java_Background_sep_1kron_1radius
  (JNIEnv *, jobject, jbyteArray, jbyteArray, jint, jint, jint, jint, jdouble, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray, jshortArray);

/*
 * Class:     Background
 * Method:    sep_ellipse_axes
 * Signature: ([D[D[D[D[D[D)V
 */
JNIEXPORT void JNICALL Java_Background_sep_1ellipse_1axes
  (JNIEnv *, jobject, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray);

/*
 * Class:     Background
 * Method:    sep_ellipse_coeffs
 * Signature: ([D[D[D[D[D[D)V
 */
JNIEXPORT void JNICALL Java_Background_sep_1ellipse_1coeffs
  (JNIEnv *, jobject, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray, jdoubleArray);

/*
 * Class:     Background
 * Method:    sep_set_ellipse
 * Signature: (Ljava/lang/String;IIDDDDDDS)V
 */
JNIEXPORT void JNICALL Java_Background_sep_1set_1ellipse
  (JNIEnv *, jobject, jstring, jint, jint, jdouble, jdouble, jdouble, jdouble, jdouble, jdouble, jshort);

/*
 * Class:     Background
 * Method:    sep_set_extract_pixstack
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_Background_sep_1set_1extract_1pixstack
  (JNIEnv *, jobject, jint);

/*
 * Class:     Background
 * Method:    sep_get_extract_pixstack
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_Background_sep_1get_1extract_1pixstack
  (JNIEnv *, jobject);

/*
 * Class:     Background
 * Method:    sep_get_errmsg
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_Background_sep_1get_1errmsg
  (JNIEnv *, jobject, jint, jstring);

/*
 * Class:     Background
 * Method:    sep_get_errdetail
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_Background_sep_1get_1errdetail
  (JNIEnv *, jobject, jstring);

#ifdef __cplusplus
}
#endif
#endif
