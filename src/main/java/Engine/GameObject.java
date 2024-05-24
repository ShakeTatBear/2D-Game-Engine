package Engine;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private String name;
    private List<Components> components;

    public GameObject(String name){
        this.name = name;
        this.components = new ArrayList<>();
    }

    public <T extends Components> T getComponent(Class<T> componentClass) {
        for (Components c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                    } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error casting component";
                    }
                }
            }
            return null;
        }

        public <T extends Components> void removeComponent(Class<T> componentClass){
            for(int i =0; i < components.size(); i++){
                Components c = components.get(i);
                if(componentClass.isAssignableFrom(c.getClass())){
                    components.remove(i);
                    return;
                }
            }
        }

        public void addComponent(Components c){
        this.components.add(c);
        c.gameObject = this;
        }

        public void update(float dt){
            for(int i=0; i < components.size(); i++){
                components.get(i).update(dt);
            }
        }

        public void start(){
            for (int i=0; i < components.size(); i++){
                components.get(i).start();
            }
        }
    }

