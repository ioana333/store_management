import PresentationLayer.MainController;
import PresentationLayer.MainView;

public class Main
{
    public static void main(String[] args)
    {
        MainView mv = new MainView();
        new MainController(mv);
    }
}
