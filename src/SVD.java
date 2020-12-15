import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class SVD {
	public double [][] svd(double [][] components, int n) {
		Matrix m = new Matrix(components);
		SingularValueDecomposition s_v_d = m.svd();
		return components;
	}
}
