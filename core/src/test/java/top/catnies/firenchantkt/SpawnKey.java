package top.catnies.firenchantkt;

import java.util.Locale;
import java.util.Scanner;

public class SpawnKey {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(sc.next().replace(".", "_").toUpperCase(Locale.ROOT));
    }
}
