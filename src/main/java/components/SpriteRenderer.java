package components;

import Engine.Components;

public class SpriteRenderer extends Components {

    private boolean  firstTime = false;

    @Override
    public void start(){
        System.out.println("I am starting.");
    }

    @Override
    public void update(float dt){
        if(!firstTime){
            System.out.println("I am updating.");
            firstTime = true;
        }
    }

}
