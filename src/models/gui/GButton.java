package models.gui;

import models.Entity;

import controllers.GamePanelController;
import controllers.MouseInputController;
import util.ImageReader;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class GButton extends Entity {

    public BufferedImage _normalImage;
    public BufferedImage _clickedImage;

    private String _content;
    private GamePanelController gamePanelController;
    private MouseInputController mouseInputController;
    private Color backgroundColor = new Color(220, 50, 50), textColor = Color.WHITE;
    private Color backgroundOverlayColor = null;
    private int delay = 50;

    private boolean isPressed = false;

    final static private BufferedImage BTN_IMAGE01 = ImageReader.Read(Path.of("GameUI", "button01.png").toString());
    private Font font;

    public GButton(String content, GamePanelController gamePanelController, MouseInputController mouseInputController) {
        super();
        this._content = content;
        isCollied = false;
        this.gamePanelController = gamePanelController;
        this.mouseInputController = mouseInputController;
        this.setName(content);

        _normalImage = BTN_IMAGE01.getSubimage(0, 0, BTN_IMAGE01.getWidth(), BTN_IMAGE01.getHeight() / 2);
        _clickedImage = BTN_IMAGE01.getSubimage(0, BTN_IMAGE01.getHeight() / 2, BTN_IMAGE01.getWidth(), BTN_IMAGE01.getHeight() / 2);
    }

    public GButton(String content, int x, int y, int width, int height, GamePanelController gamePanelController, MouseInputController mouseInputController) {
        super();
        this._x = x;
        this._y = y;
        this._width = width;
        this._height = height;
        this._content = content;
        isCollied = false;
        this.gamePanelController = gamePanelController;
        this.mouseInputController = mouseInputController;
        this.setName(content);
// default

        _normalImage = BTN_IMAGE01.getSubimage(0, 0, BTN_IMAGE01.getWidth(), BTN_IMAGE01.getHeight() / 2);
        _clickedImage = BTN_IMAGE01.getSubimage(0, BTN_IMAGE01.getHeight() / 2, BTN_IMAGE01.getWidth(), BTN_IMAGE01.getHeight() / 2);
    }

    public GButton(String content, int screenWidth, int screenHeight, GamePanelController gamePanelController, MouseInputController mouseInputController) {
        super();
        Dimension strSize1 = GString.getStringSize((Graphics2D) gamePanelController.getGraphics(), "PLAY", new Font("Roboto", Font.PLAIN, 15));
        Dimension btnSize1 = new Dimension(strSize1.width + 70, strSize1.height + 30);

        _content = content;
        _x = (screenWidth - btnSize1.width) / 2;
        _y = (screenHeight - btnSize1.height) / 2;
        _width = btnSize1.width;
        _height = btnSize1.height;

        this.gamePanelController = gamePanelController;
        this.mouseInputController = mouseInputController;

        this.setFont(new Font("Roboto", Font.PLAIN, 15));
        this.setName(content);
    }

    public GButton(String content, int x, int y, int width, int height, BufferedImage normalImage, BufferedImage clickedImage, GamePanelController gamePanelController, MouseInputController mouseInputController) {
        super();
        this._x = x;
        this._y = y;
        this._width = width;
        this._height = height;
        this._content = content;
        isCollied = false;
        this.gamePanelController = gamePanelController;
        this.mouseInputController = mouseInputController;
        this.setName(content);

        // Manual
        _normalImage = normalImage;
        _clickedImage = clickedImage;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (_normalImage != null && _clickedImage != null) {
            if (!isPressed)
                g2d.drawImage(_normalImage, _x, _y, _width, _height, this);
            else
                g2d.drawImage(_clickedImage, _x, _y, _width, _height, this);

        } else {
            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(_x, _y, _width, _height, 10, 10);
            g2d.setColor(textColor);
            if (backgroundOverlayColor != null) {
                g2d.setColor(backgroundOverlayColor);
                g2d.fillRoundRect(_x, _y, _width, _height, 10, 10);
            }
        }

        if (!_content.equals("")) {
            //draw content.
            if (g2d.getColor() != Color.white)
                g2d.setColor(Color.white);
            if (g2d.getFont() != font)
                g2d.setFont(font);

            GString.drawCenteredString(g2d, _content, new Rectangle(_x, _y, _width, _height), font);
        }
    }

    @Override
    public void update() {
        // Something...

        if (mouseInputController.isClicked()
                && _x + 5 < mouseInputController.getLocationClicked().x
                && mouseInputController.getLocationClicked().x < _x + _width - 5
                && _y + 5 < mouseInputController.getLocationClicked().y
                && mouseInputController.getLocationClicked().y < _y + _height - 5) {
            isPressed = true;

            backgroundOverlayColor = new Color(0, 0, 0, 50);

            GamePanelController.setTimeout(500, () -> {
                backgroundOverlayColor = null;
                setPressed(false);
            });
        }
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    public void setTextColor(Color color) {
        textColor = color;
    }

    public void setPressed(boolean b) {
        isPressed = b;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
