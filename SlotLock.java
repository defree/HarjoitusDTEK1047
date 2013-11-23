/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumservice.HarjoitusDTEK1047;

/**
 *
 * @author Kalle
 */
public class SlotLock {
    public boolean lock = false;
    
    public SlotLock(){
    }
    public void lock(){
        lock = true;
        //System.out.println("locked");
    }
    
    public void unlock(){
        //System.out.println("unlocked");
        lock = false;
    }
    
}
