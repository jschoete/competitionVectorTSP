package competition;

public class ClassGetter extends SecurityManager {

    public String getClassName() {
        return getClassContext()[1].getSimpleName();
    }

}
