package com.lab9;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

    @Override
    public void create() {
        //Переключение экрана на пользовательскую процедуру
        setScreen(new lab9());
    }
}

