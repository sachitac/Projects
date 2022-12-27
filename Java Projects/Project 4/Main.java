import java.io.*;
import java.util.*;

public class Main {
    public static void main(String args[]) throws FileNotFoundException {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, Player> away = new HashMap<>();
        HashMap<String, Player> home = new HashMap<>();

        keyFileParsing(map);
        //System.out.println(map);

        System.out.println("please enter in the filename");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        File file = new File(filename);
        Scanner scan = new Scanner(file);

        if (file.exists()) {
            while (scan.hasNext()) {
                String team = scan.next();
                String name = scan.next();
                String key = scan.next();

                if (map.containsKey(key)) {
                    Player p;

                    if (home.containsKey(name)) {
                        p = home.get(name);
                    } else if (away.containsKey(name)) {
                        p = away.get(name);
                    } else {
                        p = new Player(name);
                    }

                    String value = map.get(key);

                    switch (value) {
                        case "OUTS":
                            p.setOuts(p.getOuts() + 1);
                            break;
                        case "STRIKEOUT":
                            p.setStrikeouts(p.getStrikeouts() + 1);
                            break;
                        case "HITS":
                            p.setHits(p.getHits() + 1);
                            break;
                        case "WALK":
                            p.setWalks(p.getWalks() + 1);
                            break;
                        case "SACRIFICE":
                            p.setSacrifices(p.getSacrifices() + 1);
                            break;
                        case "HIT BY PITCH":
                            p.setHBPs(p.getHBPs() + 1);
                            break;
                        case "ERRORS":
                            p.setError(p.getError() + 1);
                    }

                    if (team.compareTo("H") == 0) {
                        home.put(name, p);
                    } else if (team.compareTo("A") == 0) {
                        away.put(name, p);
                    }
                }

            }
        }

        scan.close();

        System.out.println("AWAY");
        HashMap<String, Player> newAway = sortingPlayers(away, 0);
        printingPlayers(newAway);
        System.out.println();
        System.out.println("HOME");
        HashMap<String, Player> newHome = sortingPlayers(home, 0);
        printingPlayers(newHome);
        System.out.println();

        System.out.println("LEAGUE LEADERS");

        for (int i = 1; i <= 6; i++) {
            switch(i){
                case 1:
                    System.out.println("BATTING AVERAGE");
                    break;
                case 2:
                    System.out.println("ON-BASE PERCENTAGE");
                    break;
                case 3:
                    System.out.println("HITS");
                    break;
                case 4:
                    System.out.println("WALKS");
                    break;
                case 5:
                    System.out.println("STRIKEOUTS");
                    break;
                case 6:
                    System.out.println("HIT BY PITCH");
                    break;
            }
            newHome = sortingPlayers(newHome, i);
            newAway = sortingPlayers(newAway, i);

            printingLeaders(newAway,newHome,i);
            System.out.println();
            System.out.println();
            /*System.out.println("homeeeeee");
            printingPlayers(newHome);
            System.out.println("awayyyyyyy");
            printingPlayers(newAway);
            System.out.println();*/
            newHome = sortingPlayers(newHome, 0);
            newAway = sortingPlayers(newAway, 0);
        }


    }

    public static void printingLeaders(HashMap<String, Player> a, HashMap<String, Player> h, int i) {

       // Map<String, Double> awayMap = convertingToMap(a, i);
        LinkedHashMap<String, Double> awayMap = convertingToMap(a,i);
        //printingPlayers(a);
        /*Set st = (Set) awayMap.entrySet();
        Iterator it = st.iterator();
        Map.Entry<String, Double> away = (Map.Entry<String, Double>) awayMap.entrySet();*/
        //Map.Entry<String, Double> awaylist = awayMap.entrySet().iterator().next();

        //Map<String, Double> HomeMap = convertingToMap(h, i);
        LinkedHashMap<String, Double> HomeMap = convertingToMap(h,i);
        //Set set = (Set) awayMap.entrySet();
        //Iterator itr = set.iterator();
        //Map.Entry<String, Double> homelist = HomeMap.entrySet().iterator().next();
        //System.out.println(h);

        int prevTies = 0;

        double awayMax = 0;
        double homeMax = 0;

        for (int p = 1; p <= 3; p++) {
            int ties = 0;
            //Map.Entry<String, Double> awaylist = awayMap.entrySet().iterator().next();
            //Map.Entry<String, Double> homelist = HomeMap.entrySet().iterator().next();
            awayMax = awayMap.entrySet().iterator().next().getValue();
            homeMax = HomeMap.entrySet().iterator().next().getValue();

            if (awayMax == homeMax) {
                int awayTies = countingTies(awayMap, awayMax);
                int homeTies = countingTies(HomeMap, homeMax);
                ties = (awayTies - 1) + homeTies;
            } else if (awayMax > homeMax) {
                ties = countingTies(awayMap, awayMax) - 1;
            } else if (homeMax > awayMax) {
                ties = countingTies(HomeMap, homeMax) - 1;
            }

            if (awayMax == homeMax) {
                awayMap = loops(awayMap, awayMax, i, false);
                HomeMap = loops(HomeMap, homeMax, i, true);
            } else if (awayMax > homeMax) {
                awayMap = loops(awayMap, awayMax, i, false);
            } else if (homeMax > awayMax) {
                HomeMap = loops(HomeMap, homeMax, i, false);
            }

            //System.out.println("position " + p + " ties " + ties + " prevTies " + prevTies);

            if (p == 1) {
                if (ties >= 3) {
                    break;
                } else {
                    prevTies = ties;
                }
            } else if (p == 2 && prevTies != 0 || p == 2 && ties != 0) {
                break;
            }

            System.out.println();
        }
    }

    public static LinkedHashMap<String, Double> loops(LinkedHashMap<String, Double> map, double max, int i, boolean flag) {
        int k = 0;
        List<String> list = new ArrayList<String>();

        for (Map.Entry<String, Double> x : map.entrySet()) {
            if (x.getValue() == max) {
                if (k == 0 && !flag) {
                    if (i == 1 || i == 2) {
                        System.out.printf("%.3f\t%s", x.getValue(), x.getKey());
                    } else {
                        System.out.print(x.getValue().intValue() + "\t" + x.getKey());
                    }
                } else {
                    System.out.print(", " + x.getKey());
                }
                list.add(x.getKey());
            } else {
                break;
            }
            k++;
        }

        for(int p = 0; p < list.size(); p++){
            map.remove(list.get(p));
        }
        return map;
    }

    public static int countingTies(LinkedHashMap<String, Double> map, double max) {
        int ties = 0;
        int count = 0;
        for (Map.Entry<String, Double> x : map.entrySet()) {
            if (x.getValue() == max) {
                ties++;
            }
            count++;
        }

        return ties;
    }


    public static LinkedHashMap<String,Double> convertingToMap(HashMap<String, Player> map, int i) {
        LinkedHashMap<String, Double> list = new LinkedHashMap<>();

        switch (i) {
            case 1:
                for (Map.Entry<String, Player> x : map.entrySet()) {
                    list.put(x.getKey(), x.getValue().calcBattingAvg());
                }

                break;
            case 2:
                for (Map.Entry<String, Player> x : map.entrySet()) {
                    list.put(x.getKey(), x.getValue().calcOBPercentage());
                }
                break;
            case 3:
                for (Map.Entry<String, Player> x : map.entrySet()) {
                    list.put(x.getKey(), x.getValue().getHits());
                }
                break;
            case 4:
                for (Map.Entry<String, Player> x : map.entrySet()) {
                    list.put(x.getKey(), x.getValue().getWalks());
                }
                break;
            case 5:
                for (Map.Entry<String, Player> x : map.entrySet()) {
                    list.put(x.getKey(), x.getValue().getStrikeouts());
                }
                break;
            case 6:
                for (Map.Entry<String, Player> x : map.entrySet()) {
                    list.put(x.getKey(), x.getValue().getHBPs());
                }
                break;

        }

        return list;
    }

        //parsing through keyfile
        public static void keyFileParsing (HashMap < String, String > map) throws FileNotFoundException {
            File f = new File("keyfile.txt");
            Scanner sc = new Scanner(f);

            String header = "";
            String key = "";

            if (f.exists()) {
                while (sc.hasNext()) {
                    String str = sc.nextLine();
                    if (str.contains("#")) {
                        header = str.substring(3, str.length() - 3);
                        //System.out.println(header);
                    } else if (!str.isEmpty()) {
                        key = str;
                        //System.out.println(key);
                    } else if (str.isEmpty()) {
                        //System.out.println(str);
                        header = "";
                        key = "";
                    }

                    if (header.length() > 0 && key.length() > 0) {
                        //System.out.println(key + " " + header);
                        map.put(key, header);
                        //System.out.println(map);
                    }
                }
            }

            sc.close();

        }

        public static HashMap<String, Player> sortingPlayers (HashMap < String, Player > map,int n){
            List<Map.Entry<String, Player>> list = new LinkedList<Map.Entry<String, Player>>(map.entrySet());

            Collections.sort(list, new Comparator<Map.Entry<String, Player>>() {
                @Override
                public int compare(Map.Entry<String, Player> o1, Map.Entry<String, Player> o2) {
                    String num1 = "";
                    String num2 = "";

                    switch (n) {

                        case 0:
                            num1 = o1.getKey();
                            num2 = o2.getKey();
                            break;
                        case 1:
                            num1 = String.valueOf(o1.getValue().calcBattingAvg());
                            num2 = String.valueOf(o2.getValue().calcBattingAvg());
                            break;
                        case 2:
                            num1 = String.valueOf(o1.getValue().calcOBPercentage());
                            num2 = String.valueOf(o2.getValue().calcOBPercentage());
                            break;
                        case 3:
                            num1 = String.valueOf(o1.getValue().getHits());
                            num2 = String.valueOf(o2.getValue().getHits());
                            break;
                        case 4:
                            num1 = String.valueOf(o1.getValue().getWalks());
                            num2 = String.valueOf(o2.getValue().getWalks());
                            break;
                        case 5:
                            num1 = String.valueOf(o1.getValue().getStrikeouts());
                            num2 = String.valueOf(o2.getValue().getStrikeouts());
                            break;
                        case 6:
                            num1 = String.valueOf(o1.getValue().getHBPs());
                            num2 = String.valueOf(o2.getValue().getHBPs());
                            break;
                    }

                    if (n == 0 || n == 5) {
                        //System.out.println("ascending");
                        return (num1.compareTo(num2));
                    } else {
                        //System.out.println("descending");
                        return (num2.compareTo(num1));
                    }
                }

            });

            HashMap<String, Player> temp = new LinkedHashMap<String, Player>();
            for (Map.Entry<String, Player> a : list) {
                temp.put(a.getKey(), a.getValue());
            }

            return temp;
        }

        public static void printingPlayers (HashMap < String, Player > map){
            //calculate stat values

            for (Map.Entry<String, Player> name : map.entrySet()) {
                double bats = name.getValue().calcAtBats();
                double BA = name.getValue().calcBattingAvg();
                double OB = name.getValue().calcOBPercentage();

                //display individual stats of each player
                System.out.printf("%s\t", name.getValue().getName());
                System.out.print((int) bats + "\t");
                System.out.print((int) name.getValue().getHits() + "\t");
                System.out.print((int) name.getValue().getWalks() + "\t");
                System.out.print((int) name.getValue().getStrikeouts() + "\t");
                System.out.print((int) name.getValue().getHBPs() + "\t");
                System.out.print((int) name.getValue().getSacrifices() + "\t");
                System.out.printf("%.3f\t", BA);
                System.out.printf("%.3f", OB);
                System.out.println();
            }
        }
}

