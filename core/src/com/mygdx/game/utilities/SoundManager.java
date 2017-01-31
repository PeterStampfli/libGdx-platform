package com.mygdx.game.utilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by peter on 1/31/17.
 */

public class SoundManager {

    private AssetManager assetManager;
    public String fileType="wav";
    private HashMap<String,Sound> sounds;
    private Sound noSound;

    public SoundManager(AssetManager assetManager){
        this.assetManager=assetManager;
        sounds=new HashMap<String, Sound>();
        noSound=null;
    }

    public void add(String... names){
        for (String name: names) {
            assetManager.load(name + "." + fileType, Sound.class);
            sounds.put(name, noSound);
        }

    }

    public void getAll(){
        Set<String> names=sounds.keySet();
        for (String name:names) {
            sounds.put(name,(Sound) assetManager.get(name+"."+fileType));
        }
    }

    public void play(String name){
        sounds.get(name).play();
    }
}
