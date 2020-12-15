import java.awt.Color;
import javax.swing.JFrame;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.LinearRenderer2D;
import de.erichseifert.gral.plots.lines.AbstractLineRenderer2D;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

@SuppressWarnings("serial")
public class Visualization extends JFrame {
	
	public Visualization(double [][] matrix) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 500);
		
		@SuppressWarnings("unchecked")
		DataTable data = new DataTable(Double.class, Double.class);
		
		retrieve_Data(data, matrix);
		
		XYPlot p = new XYPlot(data);
		getContentPane().add(new InteractivePanel(p));
		LineRenderer l = new DefaultLineRenderer2D();
		p.setLineRenderers(data, l);
		Color color = new Color(0.0f, 0.5f, 1.0f);
		
	}
	
	public void retrieve_Data(DataTable data, double [][] matrix) {
		double [] x = new double[matrix.length];
		double [] y = new double[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			x[i] = matrix[i][0];
			y[i] = matrix[i][1];
		}
		for (int i = 0; i < x.length; i++) {
			data.add((double)x[i], (double)y[i]);
		}

	}
}
