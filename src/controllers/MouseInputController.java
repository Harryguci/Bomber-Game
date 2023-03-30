package controllers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputController implements MouseListener, MouseMotionListener {

    // will do somethings in here after...
    private boolean isClicked = false;
    private Point locationClicked = new Point(), locationMoving = new Point();

    private GamePanelController gamePanelController;

    public MouseInputController(GamePanelController gamePanelController) {
        this.gamePanelController = gamePanelController;
    }

    public boolean isClicked() {
        return this.isClicked;
    }

    public Point getLocationClicked() {
        return this.locationClicked;
    }
    public Point getLocationMoving() {
        return this.locationMoving;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        locationClicked.x = e.getX();
        locationClicked.y = e.getY();
        isClicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isClicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        locationMoving.x = e.getX();
        locationMoving.y = e.getY();

        System.out.println(locationMoving.x + " " + locationMoving.y);
    }
}
