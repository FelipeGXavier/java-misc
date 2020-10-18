package misc.files;

import java.time.LocalDate;

public class Employee {

    private String name;
    private String surname;
    private LocalDate birthDate;


    private void setName(String name) {
        this.name = name;
    }

    private void setSurname(String surname) {
        this.surname = surname;
    }

    private void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder{


        private Employee employee = new Employee();

        public Builder withName(String name){
            this.employee.setName(name);
            return this;
        }

        public Builder withSurname(String surname){
            this.employee.setSurname(surname);
            return this;
        }

        public Builder withBirthDate(LocalDate date){
            this.employee.setBirthDate(date);
            return this;
        }

        public Employee build(){
            return this.employee;
        }
    }

}
