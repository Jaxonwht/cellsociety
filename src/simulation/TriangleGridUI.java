package simulation;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;

public class TriangleGridUI extends GridUI{
    public TriangleGridUI(Grid grid) { super(grid); }

    @Override
    protected void addShape(int i, int j, int state) {
        Node temp = new Polygon();
        double sideLength = getSimulationWidth() / (getMyGrid().getNumCol() / 2);
        double height = getSimulationHeight() / getMyGrid().getNumRow();
        if ((i + j) % 2 == 0) {
            ((Polygon) temp).getPoints().addAll(new Double[] {
                    j / 2 * sideLength + ((i % 2 == 1) ? 0.5 * sideLength : 0), i * height,
                    j / 2 * sideLength + ((i % 2 == 1) ? 0.5 * sideLength : 0) + sideLength, i * height,
                    j / 2 * sideLength + ((i % 2 == 1) ? 0.5 * sideLength : 0) + sideLength / 2, i * height + height,
            });
        }
        else {
            ((Polygon) temp).getPoints().addAll(new Double[] {
                    j / 2 * sideLength + ((i % 2 == 0) ? 0.5 * sideLength : 0), i * height + height,
                    j / 2 * sideLength + ((i % 2 == 0) ? 0.5 * sideLength : 0) + sideLength, i * height + height,
                    j / 2 * sideLength + ((i % 2 == 0) ? 0.5 * sideLength : 0) + sideLength / 2, i * height,
            });
        }
        ((Polygon) temp).setFill(getIntToPaintMap().get(state));
    }

    @Override
    protected void addImageView(int i, int j, int state) {
        Node temp = new ImageView(getIntToImageMap().get(state));
        double sideLength = getSimulationWidth() / (getMyGrid().getNumCol() / 2);
        double height = getSimulationHeight() / getMyGrid().getNumRow();
        ((ImageView) temp).setFitHeight(height);
        ((ImageView) temp).setFitWidth(sideLength);
        if ((i + j) % 2 == 0) {
            ((ImageView) temp).setX(j / 2 * sideLength + ((i % 2 == 1) ? 0.5 * sideLength : 0));
            ((ImageView) temp).setY(i * height);
        }
        else {
            ((ImageView) temp).setX(j / 2 * sideLength + ((i % 2 == 0) ? 0.5 * sideLength : 0));
            ((ImageView) temp).setY(i * height);
        }
    }
}
