package components;

import Engine.Components;

public class FontRenderer extends Components {

    @Override
    public void start(){
        if (gameObject.getComponent(SpriteRenderer.class) != null){
            System.out.println("Found Font Renderer");
        }
    }

    @Override
    public void update(float dt){

    }
}
