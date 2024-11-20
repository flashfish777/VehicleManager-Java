package Managers;

import Systems.*;

public class SystemManager {
    public static SystemManager Instance;

    private SystemBase mainSystem;

    public void ChangeSystem(SystemBase system) {
        mainSystem = system;
    }

    public void Update() {
        mainSystem.Init();
    }

    public String NowUser() {
        return mainSystem.GetUserName();
    }
}
