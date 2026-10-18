package LabMouses;
import java.time.LocalDate;
public class Mouse {

    private int mouseId ; //not final cause the task description says it can change
    private final LocalDate birthDate;
    private int weight;
    private Sex sex;// same as in mouseId
    private float temperature;
    private String observations;
    private Cromosome cr1;
    private Cromosome cr2;

    public Mouse (mouseId, birthDate, weight, sex, temperature, observations, cr1, cr2){
        this.mouseId= mouseId;
        this.birthDate=birthDate;
        this.weight=weight;
        this.sex=sex;
        this.temperature=temperature;
        this.observations=observations;
        this.cr1=cr1;
        this.cr2=cr2;
    }
    //validate in the constructor correct weight temperature??

    boolean isSterile(){
        return
    }
    boolean isPolygamous(){
        return
    }

    //getter and setters, the class is gonna be mutable but there are some attributes
    //where we're not gonna give the possibility of setting


}
