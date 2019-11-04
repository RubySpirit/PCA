import enums.Binary;
import enums.Contact;
import enums.Default;
import enums.Education;
import enums.Housing;
import enums.Job;
import enums.Loan;
import enums.Marital;
import enums.Month;
import enums.POutcome;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
class BankCsvModel {
    private Double age;
    private Job job;
    private Marital marital;
    private Education education;
    private Default eDefault;
    private Double balance;
    private Housing housing;
    private Loan loan;
    private Contact contact;
    private Double day;
    private Month month;
    private Double duration;
    private Double campaign;
    private Double pDays;
    private Double previous;
    private POutcome poutcome;
    private Binary y;

    BankCsvModel(String[] attributes) {
        this.age = Double.valueOf(attributes[0]);
        this.job = Job.fromString(attributes[1]);
        this.marital = Marital.valueOf(attributes[2]);
        this.education = Education.valueOf(attributes[3]);
        this.eDefault = Default.valueOf(attributes[4]);
        this.balance = Double.valueOf(attributes[5]);
        this.housing = Housing.valueOf(attributes[6]);
        this.loan = Loan.valueOf(attributes[7]);
        this.contact = Contact.valueOf(attributes[8]);
        this.day = Double.valueOf(attributes[9]);
        this.month = Month.valueOf(attributes[10]);
        this.duration = Double.valueOf(attributes[11]);
        this.campaign = Double.valueOf(attributes[12]);
        this.pDays = Double.valueOf(attributes[13]);
        this.previous = Double.valueOf(attributes[14]);
        this.poutcome = POutcome.valueOf(attributes[15]);
        this.y = Binary.valueOf(attributes[16]);
    }


    public double[] toDoubleArray()
    {
        double dAge=this.age;
        double dJob=this.job.ordinal();
        double dMarital= this.marital.ordinal();
        double dEducation= this.education.ordinal();
        double dEDefault= this.eDefault.ordinal();
        double dBalance = this.balance;
        double dHousing = this.housing.ordinal();
        double dLoan = this.loan.ordinal();
        double dContact = this.contact.ordinal();
        double dDay = this.day;
        double dMonth = this.month.ordinal();
        double dDuration = this.duration;
        double dCampaign = this.campaign;
        double dPDay = this.pDays;
        double dPrevious = this.previous;
        double dPOutcome = this.poutcome.ordinal();
        double dY = this.y.ordinal();

        return new double[] {dAge,dJob,dMarital,dEducation,dEDefault,dBalance,dHousing,dLoan,dContact,dDay,dMonth,
            dDuration,dCampaign, dPDay,dPrevious,dPOutcome,dY};
    }
}
