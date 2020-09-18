

/**
 * @author maxim Copyright 2020
 */


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;




public class Main
{
    public static void main(String[] args)throws IOException
    {
        // prompt the user for galaxy file name and read the file name and open a scanner to read from that file
        Scanner input= new Scanner(System.in);
        System.out.println("Please enter the name of the file containing map of the galaxy.");
        String fileName1=input.nextLine();

        File file=new File(fileName1); // pass filename and open file to read
        Scanner ingalaxy=new Scanner(file); // declare scanner

        // prompt the user for routes file name, read the file and create a scanner object to read from that file
        System.out.println("Please enter the name of the file containing name of pilot routes.");
        String fileName2=input.nextLine();
        File fileroute=new File(fileName2); // pass filename and open file to read
        Scanner inroute=new Scanner(fileroute); // declare scanner

        // create a printwriter object to write to the output file
        PrintWriter writer= new PrintWriter("patrols.txt");

        // create a instance of graph
        Graph graph=new Graph();


        // read from the galaxy file line by line untile end of file is reached
        while(ingalaxy.hasNext())
        {
            // at first store the line in a string, get rid of white spaces, strip into a list, get rid of commas
            String str=ingalaxy.nextLine();
            //System.out.println(str);
            str=str.strip();
            String sarr[]=str.split("\\s+");

            //add the first word in the vertices list as its a vertex
            // same way in each line put the first word in the vertices list
            graph.vertices.add(sarr[0]);

            // create a instace of a hashmap
            HashMap<String,Double> hash=new HashMap<String,Double>();

            // the first word is the source
            // then the next each one is a combination of destinaiton and weight
            String source= sarr[0];

            // loop through each and store them in the adjency list hashmap with source, destination and weight
            for (int i=0,  j=1; j<sarr.length;j++)
            {
               String jarr[]=sarr[j].split("\\s*,\\s*");
               String dest=jarr[0];
               Double cost=Double.parseDouble(jarr[1]);
               graph.addEdge(source, dest, cost, hash);

            }

            /*
            for (int i=0; i<sarr.length; i++)
            {
                System.out.println(sarr[i]);
            }
            System.out.println("======================");
            */
        }


        // open the routes file and read from it run by line and perform operations
        while(inroute.hasNext())
        {
            String s=inroute.nextLine();
           // System.out.println(s);
            s=s.strip();
            String rarr[]=s.split("\\s+");
            String pilot=rarr[0];
            Double weight=0.0;
            for (int i=1, j=2; i<rarr.length-1 && j<rarr.length;i++,j++)
            {
                // check if the vertices are valid and in the vertices list if not set weight to 0 break
                if(!(graph.checkVertice(rarr[i])&&graph.checkVertice(rarr[j])))
                {
                    /*=========================================*/
                    weight=0.0;
                    break;
                }

                // check if theres a valid path between this two vertices if there is not set weight to 0 and break
                else if(graph.checkEdge(rarr[i], rarr[j])==null)
                {
                    weight=0.0;
                    break;
                }
                // otherwise add weight of each path to the accumlulator for pah weight of the pilot
                else
                {
                    weight+=graph.checkEdge(rarr[i], rarr[j]);
                }


            }

            // if total weight of the path is 0 then the path is invalid otherwise its valid
            String status;
            if(weight==0)
            {
                status="invalid";
            }
            else
            {
                status="valid";
            }
            //System.out.println(pilot+"\t"+weight+"\t"+status);
            // add the information to addlist function of graph to create a node and add to list
            graph.addList(pilot, status, weight);

        }

        // call display sort to sort the pilot paths by their weight and write to the output file the sorted list
        graph.displaySort(writer);
        graph.DisplayGraph();





        /*
        Graph g= new Graph();
        HashMap<String,Double> h=new HashMap<String,Double>();
        HashMap<String,Double> h2=new HashMap<String,Double>();
        HashMap<String,Double> h3=new HashMap<String,Double>();
        g.vertices.add("maxim");
        g.addEdge("maxim","UTD",2022.0,h);
        g.addEdge("maxim","DC",2016.0,h);
        g.addEdge("maxim","LAB",2014.0,h);
        //System.out.println(g.neighbors.get("maxim"));
        g.vertices.add("shawn");
        g.addEdge("shawn","brac",2022.0,h2);
        g.addEdge("shawn","dc",2016.0,h2);
        g.addEdge("shawn","lab",2014.0,h2);
        //System.out.println(g.neighbors.get("shawn"));
        g.vertices.add("hasin");
        g.addEdge("hasin","brac",2022.0,h3);
        g.addEdge("hasin","city",2016.0,h3);
        g.addEdge("hasin","lab",2014.0,h3);
        g.vertices.add("UTD");
        g.vertices.add("brac");
        g.vertices.add("lab");
        g.vertices.add("dc");
        g.vertices.add("city");
        //g.checkVertice("maxim");
        //g.checkVertice("sam");
        g.checkEdge("maxim", "UTD");
        g.checkEdge("shawn", "lab");
        g.checkEdge("hasin", "UTD");
        g.DisplayGraph();
        */

        // close the scanner and printwriter objects
        ingalaxy.close();
        inroute.close();
        writer.close();

    }

}
