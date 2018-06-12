package com.shsuger.multi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author shsuger
 * @Date 2018/5/25
 */
public class FutureTaskForMultiCompute {
    public static <T> T uncheckCall(Callable<T> callable) {
        try { return callable.call(); }
        catch (RuntimeException e) { throw e; }
        catch (Exception e) { throw new RuntimeException(e); }
    }


    public static void main(String[] args){
        FutureTaskForMultiCompute inst = new FutureTaskForMultiCompute();
        //创建任务集合
        List<FutureTask<Integer>> taskList = new ArrayList<FutureTask<Integer>>();

        //创建线程池

        ExecutorService exec = Executors.newFixedThreadPool(2);

        for(int i = 0 ;i < 10 ; i++){
            //传入Callable对象创建FutureTask对象
            FutureTask<Integer> ft = new FutureTask<Integer>(inst.new ComputeTask(i, ""+i));
            taskList.add(ft);
            // 提交给线程池执行任务，也可以通过exec.invokeAll(taskList)一次性提交所有任务;
            exec.submit(ft);
        }

        System.out.println("所有计算任务提交完毕, 主线程接着干其他事情！");

        // 开始统计各计算线程计算结果
        Integer totalResult = 0;

        Integer total = taskList.stream().map(a->uncheckCall(a::get)).reduce(Integer::sum).get();
        System.out.println(total);

       /* for (FutureTask<Integer> ft : taskList) {
            try {
                //FutureTask的get方法会自动阻塞,直到获取计算结果为止
                totalResult = totalResult + ft.get();
                System.out.println("now:  "+totalResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }*/

        // 关闭线程池
        exec.shutdown();
        System.out.println("多任务计算后的总结果是:" + totalResult);
    }

    /***
     *
     *  Callable是类似于Runnable的接口，实现Callable接口的类和实现Runnable的类都是可被其它线程执行的任务。
     *  Callable和Runnable有几点不同：
     * （1）Callable规定的方法是call()，而Runnable规定的方法是run().
     * （2）Callable的任务执行后可返回值，而Runnable的任务是不能返回值的。
     * （3）call()方法可抛出异常，而run()方法是不能抛出异常的。
     * （4）运行Callable任务可拿到一个Future对象， Future表示异步计算的结果。
     *      它提供了检查计算是否完成的方法，以等待计算的完成，并检索计算的结果。
     *      通过Future对象可了解任务执行情况，可取消任务的执行，还可获取任务执行的结果。
     *
     *
     */
    private class ComputeTask implements Callable<Integer> {

        private Integer result = 0;
        private String taskName = "";

        public ComputeTask(Integer iniResult, String taskName){
            result = iniResult;
            this.taskName = taskName;
            System.out.println("生成子线程计算任务: "+taskName);
        }

        public String getTaskName(){
            return this.taskName;
        }

        @Override
        public Integer call() throws Exception {
            // TODO Auto-generated method stub

            for (int i = 0; i < 100; i++) {
                result =+ i;
            }
            // 休眠5秒钟，观察主线程行为，预期的结果是主线程会继续执行，到要取得FutureTask的结果是等待直至完成。
            Thread.sleep(5000);
            System.out.println("子线程计算任务: "+taskName+" 执行完成!");
            return result;
        }
    }


}
