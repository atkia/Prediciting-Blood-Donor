package KNN;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


public class KNNCalculation {
    File file= new File("src/KNN/transfusion.data");
    int K ;

    int a,b,c,d ;
    double [] distance = new double[758];
    double[] sortedDis ;
    Data [] data = new Data[758];
    String r;

    public KNNCalculation(int K, int a, int b, int c, int d) throws IOException {
        this.K = K ;
        this.a=a;
        this.b = b;
        this.c = c;
        this.d = d;

        sortedDis = new double[748] ;

        init() ;


        distanceAll(a , b , c , d) ;
        result();

    }

    public void init() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file)) ;

        String str;

        for(int i=0 ; i<748 ; i++){
            str = reader.readLine() ;
           // System.out.println(str);
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
    }

    public double getDistance(int a,int b,int c,int d,int a1,int b1,int c1,int d1){
        double distance=Math.pow((a-a1),2)+Math.pow((b-b1),2)+Math.pow((c-c1),2) + Math.pow((d-d1), 2);
        distance = Math.sqrt(distance);

        return distance;
    }

    public void distanceAll(int a,int b,int c,int d){
        for(int i=0 ; i < 748 ; i++){
            distance[i] = 0;
        }

        for(int i=0 ; i < 748 ; i++){

            distance[i] = getDistance(a, b, c, d,data[i].a , data[i].b , data[i].c , data[i].d) ;
        }

        int j = 0 ;
        for(int i=0 ; i < 748 ; i++){
            sortedDis[j] = distance[i] ;

            j++ ;
        }

        Arrays.sort(sortedDis);

    }


    public String result(){
        int positive=0;
        int neg=0;

        for(int i=0 ; i<K ; i++){
            for(int j=0 ; j<748 ; j++){
                if(sortedDis[i]==distance[j]){
                    if(data[j].rslt==1){
                        positive++;

                    }
                    else if(data[j].rslt==0) {
                        neg++;
                    }
                }
            }
        }
        if( positive>neg){
            //ans ="1" ;
            System.out.println("Will Donate blood");
             r = "Will Donate blood";
             return r;
        }

        else{
        //    ans ="0" ;
            System.out.println("Will Not donate");
             r = "Will Not donate blood";
             return r;
        }


    }
}
