package KNN;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Modifyedknn {
    File file= new File("src/KNN/transfusion.data");
    int K ;
    int N ;

    int a,b,c,d;
    double [] distance;
    double[] sortedDis ;
    Data[] data;
    int nTest;
    ArrayList<String>  ans;
    int count=0;
    int startIndex,endIndex;
    double [] acc ;
    double[]pre;
    double[]r;
    double[]f;
    int totalData;
    double av=0 ;
    double prec =0;
    double rec=0;
    double fs=0;

    public Modifyedknn(int K, int N) throws IOException{
        this.K = K ;
        this.N = N ;
        countData();
        nTest = totalData/N ;

        acc = new double[N] ;
        pre = new double[N];
        r = new double[N];
        f=new double[N];
        sortedDis = new double[totalData-nTest] ;
        startIndex=totalData;
        endIndex=totalData;
        data = new Data[totalData+10];
        distance = new double[totalData+10];
        ans = new ArrayList<>(totalData+10);
        init() ;

        for(int i=0 ; i< N ; i++){
            startIndex = startIndex - nTest ;
            for(int j=startIndex ; j< endIndex ; j++){
                distanceAll(data[j].a , data[j].b , data[j].c , data[j].d) ;
                byWeight();
            }
            accuracy();
            ans.clear();
            endIndex = endIndex - nTest ;
        }


        for(int i=0 ; i<N ; i++){
            av = av + acc[i] ;
            prec = prec+pre[i];
            rec = rec+r[i];
            fs=fs+f[i];
        }

        av = av/N ;
        prec=prec/N;
        rec=rec/N;
        fs = fs/N;

        System.out.println("Avarage Accuracy: " + String.format("%.2f", av)+ "%");
        System.out.println("Precision: " + String.format("%.2f", prec) + "%");
        System.out.println("Recall: " + String.format("%.2f", rec) + "%");
        System.out.println("F1 score: "+String.format("%.2f", fs)+"%");

    }


    public void countData() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file)) ;
        String x;
        totalData=0;
        while ((x=reader.readLine())!=null){
            totalData++;
        }
    }


    public void init() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file)) ;

        String str;

        for(int i=0 ; i<totalData ; i++){
            str = reader.readLine() ;
            String[] s = str.split(",");

            String[] a = s[0].split(" ");
            String[]b = s[3].split(" ");
            int a1 = Integer.parseInt(a[0]);
            int b1 = Integer.parseInt(s[1]);
            int c1 = Integer.parseInt(s[2]);
            int d1 = Integer.parseInt(b[0]);
            int s1 = Integer.parseInt(s[4]);

            data[i]=new Data(a1,b1,c1,d1,s1);
        }

        Random rand = new Random();

        for (int i = 0; i < totalData ; i++) {
            int randomIndexToSwap = rand.nextInt(totalData);
            Data temp = data[randomIndexToSwap];
            data[randomIndexToSwap] = data[i];
            data[i] = temp;
        }
    }

    public double getDistance(int a,int b,int c,int d, int a1,int b1,int c1,int d1){
        double distance=Math.pow((a-a1),2)+Math.pow((b-b1),2)+Math.pow((c-c1),2) + Math.pow((d-d1), 2);
        distance = Math.sqrt(distance);

        return distance;
    }

    public void distanceAll(int a,int b,int c,int d){
        for(int i=0 ; i < totalData ; i++){
            distance[i] = 0;
        }

        for(int i=0 ; i < totalData-nTest ; i++){
            sortedDis[i] = -1 ;
        }

        for(int i=0 ; i < totalData ; i++){
            if(i>=startIndex && i<endIndex) continue ;

            distance[i] = getDistance(a, b, c, d,data[i].a , data[i].b , data[i].c , data[i].d) ;
        }

        int j = 0 ;
        for(int i=0 ; i < totalData ; i++){
            if(i>=startIndex && i<endIndex) continue ;

            sortedDis[j] = distance[i] ;

            j++ ;
        }
        Arrays.sort(sortedDis);

    }


    public void byWeight(){
        double[] weight = new double[K];
        int positive=0;
        int neg=0;
        /*
        for(int i=0 ; i < K ; i++){
            weight[i] = (sortedDis[totalData-nTest-1]-sortedDis[i])/(sortedDis[totalData-nTest-1]-sortedDis[0]) ;
        }

         */
        for(int i=0 ; i<K ; i++){
            for(int j=0 ; j<totalData ; j++){
                if(j>=startIndex && j<endIndex) continue ;
                if(sortedDis[i]==distance[j]){
                    if(data[j].rslt==1){
                        //   positive=positive+weight[i];
                        positive++;
                    }
                    else if(data[j].rslt==0) {
                        //  neg=neg+weight[i];
                        neg++;
                    }
                }
            }
        }
        if( positive>neg){
            ans.add("1") ;
        }

        else{
            ans.add("0") ;
        }

    }

    public void accuracy(){
        int tp=0,tn=0;
        int fp=0,fn=0;

        for(int i=0 ; i < ans.size() ; i++){
            String s= String.valueOf(data[startIndex+i].rslt);
            if(s.equals("1")){
                if( ans.get(i).equals("1")){
                    tp++;
                }
                else{
                    fn++;
                }
            }
            if(s.equals("0")){
                if( ans.get(i).equals("0")){
                    tn++;
                }
                else{
                    fp++;
                }
            }
        }

        double d = (tp+tn)*1.0/(tp+tn+fp+fn) *100;
        double recall = tp*1.0/(tp+fn);
        double p = tp*1.0/(tp+fp);
        double f1 = 2*recall*p/(recall+p)*100;


        acc[count] = d;
        pre[count]=p*100;
        r[count] =recall*100;
        f[count]=f1;
        count++ ;
    }

    public double finalAccuracy(){
        return av;
    }
    public double precision(){
        return prec;
    }
    public double recall(){
        return rec;
    }
    public double f1Score(){
        return fs;
    }

}

