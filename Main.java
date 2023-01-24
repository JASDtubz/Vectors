package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.canvas.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    private final Canvas c = new Canvas(500.0D, 500.0D);
    private final GraphicsContext gc = this.c.getGraphicsContext2D();
    private final Particle[] pixels = new Particle[441];

    public Main() { }

    public static void main(final String[] q) { Application.launch(q); }

    @Override
    public void start(final Stage stage)
    {
        for (int i = 0; i < 441; i++) { this.pixels[i] = new Particle(
                i / 21 - 10,
                i % 21 - 10,
                Math.cos(i / 21 - 10) * Math.sin(i % 21 - 10)
        ); }

        this.gc.setFill(Color.BLACK);
        this.gc.fillRect(0.0D, 0.0D, 500.0D, 500.0D);
        this.gc.setFill(Color.RED);

        this.redraw();

        final Scene scene = new Scene(new Pane(this.c));
        scene.setOnKeyPressed((e) ->
        {
            if (e.getCode() == KeyCode.ENTER) { this.loop(); }
            else if (e.getCode() == KeyCode.BACK_SPACE) { System.exit(~0 >>> 1); }
        });

        stage.setScene(scene);
        stage.setTitle("Vectors thing");
        stage.setOnCloseRequest((e) -> System.exit(~0));
        stage.show();
    }

    private void redraw()
    {
//        this.gc.setFill(Color.BLACK);
//        this.gc.fillRect(0.0D, 0.0D, 500.0D, 500.0D);
//        this.gc.setFill(Color.RED);

        for (Particle p : this.pixels)
        {
            this.gc.fillOval(p.x * 25 + 250, -p.y * 25 + 250, 1.0D, 1.0D);

            p.z += (Math.cos(p.x) * Math.sin(p.y)) * 0.001D;
            p.x += (Math.cos(Math.sin(p.x)) + p.z) * 0.001D;
            p.y += (-Math.sin(Math.cos(p.x)) + p.z) * 0.001D;
        }
    }

    private void loop()
    {
        this.redraw();

        try { Thread.sleep(10L); }
        catch (Throwable ignore) { }

        Platform.runLater(this::loop);
    }
}

class Particle
{
    public double x, y, z;

    public Particle(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
