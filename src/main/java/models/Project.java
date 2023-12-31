package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;

import java.util.*;

/**

 The Project class represents a graphical project that contains various VisualElement objects

 and provides methods to manipulate and manage those elements. The project can be drawn on a

 JavaFX GraphicsContext.
 */
public class Project {
    public String name;
    private String projectName;
    private Date updatedAt;
    private int height;
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;
    private int width;
    private final List<VisualElement> elements;
    private final Stack<VisualElement> redoStack;

    /**
     Constructs a new Project with the given project name.
     @param name The name of the project.
     */
    public Project(String name) {
        projectName = name;
        elements = new ArrayList<>();
        redoStack = new Stack<>();
        updatedAt = new Date();
        height = DEFAULT_HEIGHT;
        width = DEFAULT_WIDTH;
    }

    /**
     Constructs a new Project with the given project name, list of VisualElements,
     update date, width, and height.
     @param name The name of the project.
     @param elements The list of VisualElements to be included in the project.
     @param updatedAt The date when the project was last updated.
     @param x The width of the project canvas.
     @param y The height of the project canvas.
     */
    public Project(String name, List<VisualElement> elements, Date updatedAt, int x, int y) {
        projectName = name;
        this.elements = elements;
        redoStack = new Stack<>();
        this.updatedAt = updatedAt;
        height = y;
        width = x;
    }

    /**
     * Get the date when the project was last updated.
     *
     * @return The Date object representing the last update date.
     */
    public Date getDate() {
        return this.updatedAt;
    }

    /**
     Set the date when the project was last updated.
     @param date The Date object representing the new update date.
     */
    public void setDate(Date date) {
        this.updatedAt = date;}

    /**
     Set the project name.
     @param name The new name for the project.
     */
    public void setProjectName(String name) {
        projectName = name;
    }

    /**
     Get the project name.
     @return The name of the project.
     */
    public String getName() {
        return projectName;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setWidth(int _width) { width = _width; }
    public void setHeight(int _height) { height = _height; }

    public ArrayList<VisualElement> getElements(){
        return (ArrayList<VisualElement>) this.elements;
    }

    /**
     Get the last AardCircle from the elements list and remove it.
     @return The last AardCircle object removed from the elements list.
     */
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

    /**
     Get the last AardSquare from the elements list and remove it.
     @return The last AardSquare object removed from the elements list.
     */
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

    /**
     Add a VisualElement to the project canvas state.
     @param v The VisualElement to be added to the project's.
     */
    public void addVisualElement(VisualElement v) {
        elements.add(v);
        redoStack.clear();
    }

    /**
     Get the current FreeDrawLine element in the project.
     @return The current FreeDrawLine element, or null if not found.
     */
    public FreeDrawLine getCurrentLine(){
        VisualElement v = elements.get(elements.size() - 1);
        if(v instanceof FreeDrawLine) {
            return (FreeDrawLine) v;
        }
        return null;
    }

    /**
     Undo the last VisualElement added to the project (if any).
     */
    public void undoVisualElement() {
        if (elements.isEmpty())
            return;

        VisualElement v = elements.remove(elements.size() - 1);
        redoStack.add(v);
    }

    /**
     Redo the last undone VisualElement and add it back to the project (if any).
     @param gc The GraphicsContext on which the VisualElement should be drawn.
     */
    public void redoVisualElement(GraphicsContext gc) {
        if (!redoStack.empty()) {
            VisualElement v = redoStack.pop();
            v.draw(gc);
            elements.add(v);
        }
    }

    /**
     Draw all VisualElements in the project onto the specified GraphicsContext.
     @param gc The GraphicsContext on which the VisualElements should be drawn.
     */
    public void draw(GraphicsContext gc) {
        for (VisualElement element : elements)
            element.draw(gc);
    }

    /**
     Convert the Project properties to a dictionary format.
     @return A HashMap containing the project's properties in dictionary format.
     */
    @SuppressWarnings("unchecked")
    public JSONObject toDict() {
        JSONObject dictionary = new JSONObject();

        dictionary.put("ProjectName", projectName);
        dictionary.put("UpdateDate", updatedAt.toString());
        dictionary.put("Width", width);
        dictionary.put("Height", height);

        List<HashMap<String, Object>> elementList = new ArrayList<>();
        for (VisualElement element : elements) {
            elementList.add(element.toDict());
        }
        dictionary.put("VisualElements", elementList);

        return dictionary;
    }

    public List<HashMap<String, Object>> toDictElements() {
        List<HashMap<String, Object>> elementList = new ArrayList<>();
        for (VisualElement element : elements) {
            elementList.add(element.toDict());
        }
        return elementList;
    }

    public Stack<VisualElement> getRedoStack() {
        return this.redoStack;
    }
}





