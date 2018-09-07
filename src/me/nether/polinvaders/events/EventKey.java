package me.nether.polinvaders.events;

import com.darkmagician6.eventapi.events.Event;

public class EventKey implements Event {

    public int key;

    public EventKey(int key) {
        this.key = key;
    }

}
