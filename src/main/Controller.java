package main;

import signup.*;

public class Controller {

    /**
     * 点击注册按钮时弹出注册窗口
     */
    public void SignUp() {
        SignUpFace signUpFace = new SignUpFace();
        signUpFace.showSignUpFace();
    }
}
