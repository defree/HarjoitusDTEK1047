/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumservice;

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
            ++n;
            sum+=number;
            notifyAll();
    }
    
    public int GetSum() {
        return sum;
    }
    
    public int GetN(){
        return n;
    }
    
    public synchronized void empty()
    {
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
        this.sum = 0;
        this.n = 0;
        //notifyAll();
    }
            
    
    
}
