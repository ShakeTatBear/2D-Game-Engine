package Engine;

import Engine.util.Time;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;
    private long glfwWindow;
    public float r,g,b,a;

    public static Window window = null;

    private static Scene currentScene;

    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "2D Game Engine";
        r = 1;
        g = 1;
        b = 1;
        a = 0;
    }

    public static void changeScene(int newScene){
        switch(newScene){
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                currentScene.start();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false : "Unknown Scene '" + newScene + "'";
        }
    }

    public static Window get(){
        if(Window.window == null){
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run(){
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        init();
        loop();

        //Free the memory when loop exits
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate GLFW
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init(){
        //Set up an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to init GLFW.");
        }



        //Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //Create Window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        //Make OPENGL context current
        glfwMakeContextCurrent(glfwWindow);
        //Enable V-Sync
        glfwSwapInterval(0);

        //Make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        Window.changeScene(0);
    }

    public void loop(){
        float beginTime = Time.getTime();
        float endTime = Time.getTime();
        float dt = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)){
            //POLL events
            glfwPollEvents();

            glClearColor(r,g,b,a);
            glClear(GL_COLOR_BUFFER_BIT);

            if(dt >= 0) {
                currentScene.update(dt);
            }

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
