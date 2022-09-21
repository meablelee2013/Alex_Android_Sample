import org.gradle.api.Plugin;
import org.gradle.api.Project;

class MyPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        System.out.println("hello,this is MyPlugin");
    }
}

