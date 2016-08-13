package com.tigerit.exam;


import static com.tigerit.exam.IO.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
class TableInfo
{
    String shortName;
    String tableName;
    Integer Data;
    Integer Column;
    String ColumnName[];
    Integer dataValue[][];
    TreeMap columnNameMap;
    public TableInfo(String tableName, Integer numColumn, Integer numData) 
    {
        Data = numData;
        Column = numColumn;
        this.tableName = tableName;
        initialize();
    }
    void initialize()
    {
        ColumnName = new String[Column];
        dataValue=new Integer[Data][Column];
        columnNameMap=new TreeMap();
    }
    public void setDataValue(Integer row, Integer column, Integer data)
    {
        dataValue[row][column]=data;
    }
    public void setColumnName(String str[])
    {
        Integer i,j;
        for(i=0;i<str.length;i++)
        {
            columnNameMap.put(str[i], i);
            ColumnName[i]=str[i];
        }
    }
}

class ArrayListComparator implements Comparator<ArrayList<Integer>> 
{
    @Override
    public int compare(ArrayList<Integer> a, ArrayList<Integer> b) {
        for(Integer k=0; k<a.size(); k++){
            if(a.get(k).compareTo(b.get(k))==0) {
            } else return a.get(k).compareTo(b.get(k));
        }
        return 0;
    }
}

public class Solution implements Runnable 
{
    String targetTable1,targetTable2, targetColumn1,targetColumn2;
    String table1,table2,shortName1,shortName2;
    Integer numPrint=0,numTable,checkPrint;
    TableInfo tableinfo[];
    String printColumn[]=new String[1005];
    String printColumn1[]=new String[1005];
    void tableCreate()
    {
        Integer i,j,k;
        numTable = readLineAsInteger();
        tableinfo= new TableInfo[numTable];
        for(i=0;i<numTable;i++)
        {
            String tableName=readLine();
            String inputLine=readLine();
            StringTokenizer token=new StringTokenizer(inputLine, " ");
            Integer numColumn=Integer.parseInt(token.nextToken());
            Integer numData=Integer.parseInt(token.nextToken());
            tableinfo[i]=new TableInfo(tableName,numColumn, numData);
            String columnName[]=new String [numColumn];
            inputLine=readLine();
            StringTokenizer token1=new StringTokenizer(inputLine, " ");
            for(j=0;j<numColumn;j++)
            {
                columnName[j]=token1.nextToken();
            }
            tableinfo[i].setColumnName(columnName);
            for(j=0;j<numData;j++)
            {
                inputLine=readLine();
                StringTokenizer token2=new StringTokenizer(inputLine, " ");
                for(k=0;k<numColumn;k++)
                {

                    String temp=token2.nextToken();
                    //System.out.println("data: "+temp);
                    Integer x=Integer.parseInt(temp);
                    tableinfo[i].setDataValue(j, k, x);
                }
            }           
        }
    }
    Integer printType(String str)
    {
        Integer i,j;
        for(i=0;i<str.length();i++)
        {
            if(str.charAt(i)=='*')
            {
                return 1;
            }
        }
        return 0;
    }
    void getQuery()
    {
        numPrint=0;
        Integer numQuery=readLineAsInteger();
        Integer i,j,k;
        for(i=0;i<numQuery;i++)
        {
            checkPrint=0;
            String query=readLine();
            checkPrint=printType(query);
            numPrint=0;
            if(checkPrint==0)
            {
                StringTokenizer token2=new StringTokenizer(query, " ,");
                token2.nextToken();
                while(token2.hasMoreTokens())
                {
                    printColumn[numPrint]=token2.nextToken();
                    StringTokenizer token6=new StringTokenizer(printColumn[numPrint], ".");
                    token6.nextToken();
                    printColumn1[numPrint]=token6.nextToken();
                    numPrint++;
                    //System.out.println(printColumn[numPrint-1]);
                }
            }
            query=readLine();
            StringTokenizer token2=new StringTokenizer(query, " ");
            token2.nextToken();
            table1=token2.nextToken();
            shortName1=null;
            while(token2.hasMoreTokens())
            {
                shortName1=token2.nextToken();
            }
            query=readLine();
            token2=new StringTokenizer(query, " ");
            token2.nextToken();
            table2=token2.nextToken();
            shortName2=null;
            while(token2.hasMoreTokens())
            {
                shortName2=token2.nextToken();
            }
            //System.out.println("table1: "+table1+",table2: "+table2+"");
            //System.out.println("short1: "+shortName1+",short2: "+shortName2+"");
            query = readLine();
            token2 = new StringTokenizer(query, " .=");
            token2.nextToken();
            targetTable1 = token2.nextToken();
            targetColumn1 = token2.nextToken();
            targetTable2 = token2.nextToken();
            targetColumn2 = token2.nextToken();
            //System.out.println("targettable1: "+targetTable1+",targettable2: "+targetTable2+"");
            //System.out.println("targetcolumn1: "+targetColumn1+",targetColumn2: "+targetColumn2+"");
            
            joinOperation();
            readLine();
        }
        
    }
    void joinOperation()
    {
        Integer i,j,k;
        TableInfo tableObject1=null, tableObject2=null;
        for(i=0;i<numTable;i++)
        {
            if(tableinfo[i].tableName.equals(table1))
            {
                //System.out.println(table1+" found in "+i);
                tableObject1=tableinfo[i];
                tableObject1.shortName=shortName1;
            }
            else if(tableinfo[i].tableName.equals(table2))
            {
                //System.out.println(table2+" found in "+i);
                tableObject2=tableinfo[i];
                tableObject2.shortName=shortName2;
            }
        }
        Integer numMatch=0;
        Integer matchColumn1,matchColumn2;
        if(targetTable1.equals(table1)||targetTable1.equals(shortName1))
        {
            //System.out.println("hukkahuya");
            matchColumn1=(Integer)tableObject1.columnNameMap.get(targetColumn1);
            matchColumn2=(Integer)tableObject2.columnNameMap.get(targetColumn2);
        }
        else
        {
            matchColumn1=(Integer)tableObject1.columnNameMap.get(targetColumn2);
            matchColumn2=(Integer)tableObject2.columnNameMap.get(targetColumn1);
        }
        //System.out.println(matchColumn1+" "+matchColumn2);
        for(i=0;i<tableObject1.Data;i++)
        {
            for(j=0;j<tableObject2.Data;j++)
            {
                if(Objects.equals(tableObject1.dataValue[i][matchColumn1], tableObject2.dataValue[j][matchColumn2]))
                {
                    numMatch++;
                }
            }
        }
        //System.out.println("Match: "+matchColumn1);
        
        TableInfo newtable=new TableInfo("new table", tableObject1.Column+tableObject2.Column, numMatch);
        String str1[]=new String[tableObject1.Column+tableObject2.Column];
        for(i=0;i<tableObject1.Column;i++)
        {
            str1[i]=tableObject1.ColumnName[i];
            //System.out.println("alu "+i+": "+str1[i]);
        }
        for(;i<tableObject1.Column+tableObject2.Column;i++)
        {
            str1[i]=tableObject2.ColumnName[i-tableObject1.Column];
            //System.out.println("alu "+i+": "+str1[i]);
        }
        newtable.setColumnName(str1);
        Integer c=0,l;
        int xx=0;
        int yy=0;
        String str[]=new String [tableObject1.Column+tableObject2.Column];
        TableInfo tempTable1=new TableInfo(tableObject1.tableName, tableObject1.Column,numMatch);
        TableInfo tempTable2=new TableInfo(tableObject2.tableName,tableObject2.Column, numMatch);
        tempTable1.setColumnName(tableObject1.ColumnName);
        tempTable2.setColumnName(tableObject2.ColumnName);
        //System.out.println("OBJ: "+tableObject1.Data+" "+tableObject2.Data);
        //System.out.println("OBJ1: "+tableObject1.Column+" "+tableObject2.Column);
        
        for(i=0;i<tableObject1.Data;i++)
        {
            for(j=0;j<tableObject2.Data;j++)
            {
                yy=0;
                if(Objects.equals(tableObject1.dataValue[i][matchColumn1], tableObject2.dataValue[j][matchColumn2]))
                {
                    //System.out.println(i+" "+tableObject1.Column);
                    for(k=0;k<tableObject1.Column;k++)
                    {
                            Integer aa=tableObject1.dataValue[i][k];
                            newtable.setDataValue(xx, yy, aa);
                            //System.out.println("A: "+i+" "+k);
                            tempTable1.setDataValue(xx,k,aa);
                            yy++;
                    }
                    
                    for(k=0;k<tableObject2.Column;k++)
                    {
                           Integer aa=tableObject2.dataValue[j][k];
                            newtable.setDataValue(xx, yy, aa);
                            tempTable2.setDataValue(xx,k,aa);
                            yy++;
                    }    
                    xx++;
                }
            }
        }
        ArrayList<ArrayList<Integer>> listOfLists = new ArrayList<ArrayList<Integer>>();
        Integer point=0;
        Integer flag=0;
        if(checkPrint==1)
        {
            for(k=0;k<newtable.Data;k++)
            {
                ArrayList <Integer> arr=new ArrayList<Integer>(); 
                for(l=0;l<newtable.Column;l++)
                {
                    arr.add(newtable.dataValue[k][l]);
                }
                listOfLists.add(arr);
            }
        }
        else
        {
            
            for(k=0;k<newtable.Data;k++)
            {
                flag=0;
                ArrayList <Integer> arr=new ArrayList<Integer>();
                for(j=0;j<numPrint;j++)
                {
                    String strTemp=printColumn[j];
                    StringTokenizer token2=new StringTokenizer(strTemp, ".");
                    String temp1=token2.nextToken();
                    String temp2=token2.nextToken();
                    if(tableObject1.shortName.equals(temp1)||tableObject1.tableName.equals(temp1))
                    {
         
                        Integer index=(Integer) tempTable1.columnNameMap.get(temp2);
                        arr.add(tempTable1.dataValue[k][index]);
                    }
                    else if(tableObject2.shortName.equals(temp1)||tableObject2.tableName.equals(temp1))
                    {
                        
                        Integer index=(Integer) tempTable2.columnNameMap.get(temp2);
                        arr.add(tempTable2.dataValue[k][index]);
                    }
                    //newtable.columnNameMap.get(strTemp);
                    //arr.add(newtable.dataValue[k][index]);
                }
                listOfLists.add(arr);
            }
        }
        Collections.sort(listOfLists, new ArrayListComparator());
        if(checkPrint==1)
        {
            for(k=0;k<newtable.ColumnName.length;k++)
            {
                if(k==0)
                    System.out.print(newtable.ColumnName[k]);
                else
                    System.out.print(" "+newtable.ColumnName[k]);                    
            }
        }
        else
        {
            
                for(k=0;k<numPrint;k++)
                {
                    if(k==0)
                        System.out.print(printColumn1[k]);
                    else
                        System.out.print(" "+printColumn1[k]);                    
                }
            
            
        }
        System.out.println();
        for(k=0;k<newtable.Data;k++)
        {
            for(l=0;l<listOfLists.get(k).size();l++)
            {
                
                if(l==0)
                    System.out.print(listOfLists.get(k).get(l));
                else
                    System.out.print(" "+listOfLists.get(k).get(l));                    
            }
            System.out.println();
        }
        System.out.println();
    }
    @Override
    public void run() 
    {
        Integer testCase,i;
        //String string = readLine();
        testCase = readLineAsInteger();
        for(i = 1; i <= testCase; i++) 
        {
            System.out.println("Test: "+i);
            tableCreate();
            getQuery();
        }

        //Integer integer = readLineAsInteger();
        //printLine("Test");
        // sample output process
        //printLine(string);
        //printLine(integer);
    }
}
