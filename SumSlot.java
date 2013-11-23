/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumservice.HarjoitusDTEK1047;

/**
 *
 * @author Kalle
 */
public class SumSlot {
    private int n;
    private int sum;
    public SlotLock lock;
    
    public SumSlot(SlotLock lock) {
        n = 0;
        sum = 0;
        this.lock = lock;
    }
    
    public synchronized void Save(int number) {
    //    System.out.println(lock.lock);
        while (lock.lock)
        {
            try
            {
                wait();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
            //lock.lock();
            n++;
            sum+=number;
            //notifyAll();
    }
    
    public  synchronized int GetSum() {
        return sum;
    }
    
    public synchronized int GetN(){
        return n;
    }
            
}
