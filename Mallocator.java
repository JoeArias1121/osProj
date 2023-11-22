import java.util.*;
import java.io.*;

public class Mallocator{
    public static void main(String[] args){

        ArrayList<Integer> ar = new ArrayList<>();
        int size = -1;
        int start = -1;
        int end = -1;
        int count = 0;
        ArrayList<Integer> rstart = new ArrayList<>();
        ArrayList<Integer> limit = new ArrayList<>();

        ArrayList<Integer> pId = new ArrayList<>();
        ArrayList<Integer> pSize = new ArrayList<>();

        try {
            Scanner myReader = new Scanner(new File("Minput.txt"));
            while (myReader.hasNextLine()) {
                while (myReader.hasNext()){
                    String data = myReader.next();
                    if(size==-1){
                        size=Integer.parseInt(data);
                    }else{
                        if(start==-1){
                            start = Integer.parseInt(data);
                        }else{
                            end = Integer.parseInt(data);
                            rstart.add(start);
                            limit.add(end);
                            end = -1;
                            start = -1;
                        }
                    }
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        size = -1;
        try {
            Scanner myReader = new Scanner(new File("Pinput.txt"));
            while (myReader.hasNextLine()) {
                while (myReader.hasNext()){
                    String data = myReader.next();
                    if(size==-1){
                        size=Integer.parseInt(data);
                    }else{
                        if(start==-1){
                            start = Integer.parseInt(data);
                        }else{
                            end = Integer.parseInt(data);
                            pId.add(start);
                            pSize.add(end);
                            end = -1;
                            start = -1;
                        }
                    }
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("This will be ff:");
        ArrayList t1 = (ArrayList)rstart.clone();
        ArrayList t2 = (ArrayList)limit.clone();
        ArrayList t3 = (ArrayList)pId.clone();
        ArrayList t4 = (ArrayList)pSize.clone();
        ff(t1, t2, t3, t4);

        System.out.println("This will be bf:");
        t1 = (ArrayList)rstart.clone();
        t2 = (ArrayList)limit.clone();
        t3 = (ArrayList)pId.clone();
        t4 = (ArrayList)pSize.clone();
        bf(t1, t2, t3, t4);

        System.out.println("This will be wf:");
        t1 = (ArrayList)rstart.clone();
        t2 = (ArrayList)limit.clone();
        t3 = (ArrayList)pId.clone();
        t4 = (ArrayList)pSize.clone();
        wf(t1, t2, t3, t4);
    }

    public static void ff(ArrayList<Integer> rstart, ArrayList<Integer> limit,ArrayList<Integer> pId, ArrayList<Integer> pSize){

        ArrayList<String> unallocated = new ArrayList<>();
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        int n=rstart.size();
        fill(ans,n);

        while(pId.size()>0){
            for(int i = 0; i<rstart.size(); i++){
                if((limit.get(i)-rstart.get(i))>pSize.get(0)){
                    ans.get(i).add(rstart.get(i)+" "+(rstart.get(i)+pSize.get(0))+" "+pId.get(0));
                    rstart.set(i,rstart.get(i)+pSize.get(0));
                    pId.remove(0);
                    pSize.remove(0);
                    break;
                }else if(i==(rstart.size()-1)){
                    unallocated.add(("-"+pId.get(0)));
                    pId.remove(0);
                    pSize.remove(0);
                }
            }
        }

        if(unallocated.size()==0){
            unallocated.add("-0");
        }

        try{
            PrintWriter pw = new PrintWriter(new FileWriter("ffOut.txt"));
            for(int i=0;i<ans.size();i++){
                for(int j=0;j<ans.get(i).size();j++){
                pw.println(ans.get(i).get(j));
                }
            }
            for(int i = 0;i<unallocated.size();i++){
                pw.println(unallocated.get(i)+" ");
            }
            pw.close();
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println(ans);
        System.out.println(unallocated);

    }

    public static void bf(ArrayList<Integer> rstart, ArrayList<Integer> limit,ArrayList<Integer> pId, ArrayList<Integer> pSize){

        ArrayList<String> unallocated = new ArrayList<>();
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        int rBest = -1;
        int indBest = 0;
        int n=rstart.size();
        fill(ans,n);

        while(pId.size()>0){
            for(int i = 0; i<rstart.size(); i++){
                int space = limit.get(i)-rstart.get(i);
                if(rBest==-1 && pSize.get(0)<=space){
                    rBest = space;
                    indBest = i;
                }else if(space<rBest && pSize.get(0)<=space){
                    rBest=space;
                    indBest=i;
                }
            }
            if(rBest!=-1){
                ans.get(indBest).add(rstart.get(indBest)+" "+(rstart.get(indBest)+pSize.get(0))+" "+pId.get(0));
                rstart.set(indBest,(rstart.get(indBest)+pSize.get(0)));
                pId.remove(0);
                pSize.remove(0);
                rBest=-1;
                indBest=0;
            }else{
                unallocated.add(("-"+pId.get(0)));
                pId.remove(0);
                pSize.remove(0);
            }
        }

        if(unallocated.size()==0){
            unallocated.add("-0");
        }

        System.out.println(ans);
        if(unallocated.size()==0){
            unallocated.add("-0");
            System.out.println(unallocated);
        }else{
            System.out.println(unallocated);
        }

        try{
            PrintWriter pw = new PrintWriter(new FileWriter("bfOut.txt"));
            for(int i=0;i<ans.size();i++){
                for(int j=0;j<ans.get(i).size();j++){
                pw.println(ans.get(i).get(j));
                }
            }
            for(int i = 0;i<unallocated.size();i++){
                pw.println(unallocated.get(i)+" ");
            }
            pw.close();
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static void wf(ArrayList<Integer> rstart, ArrayList<Integer> limit,ArrayList<Integer> pId,           ArrayList<Integer> pSize){

        ArrayList<String> unallocated = new ArrayList<>();
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        int rBest = -1;
        int indBest = 0;
        int n=rstart.size();
        fill(ans,n);

        while(pId.size()>0){
            for(int i = 0; i<rstart.size(); i++){
                int space = limit.get(i)-rstart.get(i);
                if(rBest==-1 && pSize.get(0)<=space){
                    rBest = space;
                    indBest = i;
                }else if(space>rBest && pSize.get(0)<=space){
                    rBest=space;
                    indBest=i;
                }
            }
            if(rBest!=-1){
                ans.get(indBest).add(rstart.get(indBest)+" "+(rstart.get(indBest)+pSize.get(0))+" "+pId.get(0));
                rstart.set(indBest,(rstart.get(indBest)+pSize.get(0)));
                pId.remove(0);
                pSize.remove(0);
                rBest=-1;
                indBest=0;
            }else{
                unallocated.add(("-"+pId.get(0)));
                pId.remove(0);
                pSize.remove(0);
            }
        }

        System.out.println(ans);
        if(unallocated.size()==0){
            unallocated.add("-0");
            System.out.print(unallocated);
        }else{
            System.out.println(unallocated);
        }

        try{
            PrintWriter pw = new PrintWriter(new FileWriter("wfOut.txt"));
            for(int i=0;i<ans.size();i++){
                for(int j=0;j<ans.get(i).size();j++){
                pw.println(ans.get(i).get(j));
                }
            }
            for(int i = 0;i<unallocated.size();i++){
                pw.println(unallocated.get(i)+" ");
            }
            pw.close();
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static void fill(ArrayList<ArrayList<String>> ar, int n){
        for(int i=0;i<n;i++){
            ar.add(new ArrayList<String>());
        }
    }
    
}