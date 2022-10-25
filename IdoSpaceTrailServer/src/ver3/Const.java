/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver3;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;


/**
 *
 * @author idole
 */
public class Const 
{
    public static final String SHOW_POSBILE_MOVES_TURN  = "Show posbile moves turn";
    public static final String SHOW_POSBILE_MOVES_NO_TURN  = "Show posbile moves no turn";
    public static final String SET_MOVE_AND_SWITCH  = "Set move and switch";
    public static final String DO_MOVE  = "do move";
    public static final String WELCOME = "#WELCOME";
    public static final String LOGIN = "#LOGIN";
    public static final String SET_TITLE  = "set titele";
    public static final String GAME_OVER  = "game over";
    public static final String INIT_STATE_BEFORE_WIN  = "init state before win";
    public static final String YOUR_TURN  = "your turn";
    public static final String PARTNER_TURN  = "partner turn";
    public static final String CLIENT_EXIT  = "client exit";
    public static final String PARTNER_EXIT  = "partner exit";
    public static final Font DEFAULT_FONT = new JLabel().getFont();
    public static final Font LOGIN_ERR_FONT = new Font(null, Font.BOLD, DEFAULT_FONT.getSize()+1);
    public static final Color LOGIN_BGCOLOR_OK = Color.GREEN; 
    public static final Color LOGIN_BGCOLOR_ERR = Color.RED;
    public static final String WAIT_FOR_PARTNER = "wait for partner";
    
}
