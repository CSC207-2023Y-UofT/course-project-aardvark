package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class Project {
    private String projectName;
    private Date updatedAt;
    private int height;
    public static int DEFAULT_HEIGHT = 600;
    private int width;
    public static int DEFAULT_WIDTH = 800;
    private List<VisualElement> elements;
    private Stack<VisualElement> redoStack;


    public Project(String name) {
        projectName = name;
        elements = new ArrayList<>();
        redoStack = new Stack<>();
        updatedAt = new Date();
        height = DEFAULT_HEIGHT;
        width = DEFAULT_WIDTH;
    }

    public Project(String name, List<VisualElement> elements, Date updatedAt, int x, int y) {
        projectName = name;
        this.elements = elements;
        redoStack = new Stack<>();
        this.updatedAt = updatedAt;
        height = y;
        width = x;
    }

    public Date getDate() {
        return this.updatedAt;
    }

    public void setDate(Date date) {
        this.updatedAt = date;}

    public void setProjectName(String name) {
        projectName = name;
    }

    public String getName() {
        return projectName;
    }

    public ArrayList<VisualElement> getElements(){
        return (ArrayList<VisualElement>) this.elements;
    }

    public AardCircle getLastAndRemoveCircle() {
        AardCircle tmp = new AardCircle(0, 0, 0, true, true, Color.AQUA, Color.AQUA, 0);
        for (int i = elements.size() - 1; i >= 0; --i) {
            if (elements.get(i) instanceof AardCircle) {
                AardCircle t = (AardCircle)elements.get(i);
                tmp = new AardCircle(t.x, t.y, t.r, t.isFill, t.isStroke, t.fill, t.stroke, t.strokeSize);
                elements.remove(i);
                break;
            }
        }
        return tmp;
    }

    public AardSquare getLastAndRemoveSquare() {
        AardSquare tmp = new AardSquare(0, 0, 0, true, true, Color.AQUA, Color.AQUA, 0);
        for (int i = elements.size() - 1; i >= 0; --i) {
            if (elements.get(i) instanceof AardSquare) {
                AardSquare t = (AardSquare)elements.get(i);
                tmp = new AardSquare(t.x, t.y, t.r, t.isFill, t.isStroke, t.fill, t.stroke, t.strokeSize);
                elements.remove(i);
                break;
            }
        }
        return tmp;
    }

    public void addVisualElement(VisualElement v) {
        elements.add(v);
        redoStack.clear();
    }

    public FreeDrawLine getCurrentLine(){
        VisualElement v = elements.get(elements.size() - 1);
        if(v instanceof FreeDrawLine) {
            return (FreeDrawLine) v;
        }
        return null;
    }

    public Stack<VisualElement> getRedoStack(){
        return redoStack;
    };

    public void undoVisualElement() {
        if (elements.isEmpty())
            return;

        VisualElement v = elements.remove(elements.size() - 1);
        redoStack.add(v);
    }

    /**
     * If redoStack is not empty, adds the last undone
     * visualElement back to the element list.
     * Otherwise, does nothing.
     */
    public void redoVisualElement(GraphicsContext gc) {
        if (!redoStack.empty()) {
            VisualElement v = redoStack.pop();
            v.draw(gc);
            elements.add(v);
        }
    }

    public void draw(GraphicsContext gc) {
        for (VisualElement element : elements)
            element.draw(gc);
    }

    /**
     *
     * @return Hashmap containing a
     */
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> dictionary = new HashMap<>();

        dictionary.put("ProjectName", projectName);
        dictionary.put("UpdateDate", updatedAt);
        dictionary.put("Width", width);
        dictionary.put("Height", height);

        List<HashMap<String, Object>> elementList = new ArrayList<>();
        for(int i = 0; i < elements.size(); i++) {
            elementList.add(elements.get(i).toDict());
        }
        dictionary.put("VisualElements", elementList);

        return dictionary;
    }
}

