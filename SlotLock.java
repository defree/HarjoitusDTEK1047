/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumservice;

/**
 *
 * @author Kalle
 */
public class SlotLock {
    public boolean lock = false;
    
    public SlotLock(){
    }
    public void lock(){
        System.out.println("locked");
        lock = false;
    }
    
    public void unlock(){
        System.out.println("unlocked");
        lock = false;
    }
    
}
