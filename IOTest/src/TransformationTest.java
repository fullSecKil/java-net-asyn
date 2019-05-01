import java.util.ArrayList;
import java.util.List;

/**
 * @file: TransformationTest.class
 * @author: Dusk
 * @since: 2019/4/25 22:28
 * @desc:
 */
public class TransformationTest {
    public static void main(String[] args) {
        Girl g1 = new MMGirl();
        g1.smile();
        System.out.println(((MMGirl) g1).name);

        Girl g2 = new Girl();
        // MMGirl mmg1 = (MMGirl)g2;
        if (g2 instanceof MMGirl){
            g2.smile();
        } else {
            System.out.println("mm1");
        }

        Girl g3 = new Girl();
        Girl g4 = new MMGirl();
        List<Girl> glist = new ArrayList<>();
        glist.add(g3);
        glist.add(g4);
        glist.stream().filter(g->g instanceof MMGirl).map(Girl::getName).forEach(System.out::print);

    }
}

class Girl {

    public String name = "ch";

    public void smile() {
        System.out.println("girl smile()");
    }

    public String getName() {
        return name;
    }
}

class MMGirl extends Girl {

    public String name = "syf";

    @Override
    public void smile() {
        System.out.println("MMirl smile sounds sweet..");
    }

    public void c() {
        System.out.println("MMirl c()");
    }

    @Override
    public String getName() {
        return name;
    }
}