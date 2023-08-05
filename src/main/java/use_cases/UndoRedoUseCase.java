package use_cases;

import javafx.scene.canvas.GraphicsContext;

public class UndoRedoUseCase {
    private GraphicsContext gc;

    public UndoRedoUseCase(GraphicsContext gc) {
        this.gc = gc;
    }


}
