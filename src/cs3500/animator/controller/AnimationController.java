package cs3500.animator.controller;

import javax.swing.Timer;
import cs3500.IAnimation;
import cs3500.IController;
import cs3500.IView;

/**
 * Class that acts as the controller in the MVC format. Interfaces the model with the view.
 */
public class AnimationController implements IController {
  IView view;
  IAnimation a;
  Timer t;
  int speed;
  int currentTick;
  boolean paused;
  boolean loop;
  int maxTick;

  /**
   * Constructor for the animation controller.
   * @param v view to show
   */
  public AnimationController(IView v) {
    this.view = v;
    this.paused = true;
    this.currentTick = 0;
    this.loop = false;
    this.speed = 1;
  }

  @Override
  public void playAnimation(IAnimation a, String viewType, int tempo) {
    this.a = a;
    this.speed = tempo;
    this.maxTick = a.getMaxTick();
    if (viewType.equals("edit")) {
      view.addClickListener(this);
    }
    view.makeVisible();
    if (viewType.equals("visual") || viewType.equals("edit")) {
      t = new Timer(1000 / speed, e -> execute());
      t.start();
      t.setRepeats(true);
    }
    else {
      execute();
    }
  }

  /**
   * Executes the view.
   */
  private void execute() {
    if (!paused) {
      if ((currentTick == maxTick + 1) && loop) {
        currentTick = 0;
        a.resetAnimation();
        maxTick = a.getMaxTick();
      }
      view.execute();
      a.executeTick(currentTick);
      currentTick++;
    }
  }

  @Override
  public void executeTick(int tick) {
    this.currentTick = tick;
    a.executeTick(tick);
    view.execute();
  }

  @Override
  public void changeSpeed(String type) {
    if (type.equals("+")) {
      t.setDelay(1000 / speed++);
    }
    else {
      if (speed > 1) {
        t.setDelay(1000 / speed--);
      }
    }
  }

  @Override
  public boolean getPaused() {
    return paused;
  }

  @Override
  public void setPaused() {
    paused = !paused;
  }

  @Override
  public boolean getLoop() {
    return loop;
  }

  @Override
  public void setLoop() {
    loop = !loop;
  }

  @Override
  public int getSpeed() {
    return this.speed;
  }

  @Override
  public void setMaxTick(int maxTick) {
    this.maxTick = maxTick;
  }

  @Override
  public void restart() {
    this.currentTick = 0;
    this.maxTick = a.getMaxTick();
    a.resetAnimation();
  }
}
