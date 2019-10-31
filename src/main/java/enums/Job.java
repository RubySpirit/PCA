package enums;

public enum Job {
    admin,
    unknown,
    unemployed,
    management,
    housemaid,
    entrepreneur,
    student,
    blueCollar,
    selfEmployed,
    retired,
    technician,
    services;

    public static Job fromString(String s)
    {
        switch(s)
        {
            case "admin": return admin;
            case "unknown": return unknown;
            case "unemployed": return unemployed;
            case "management": return management;
            case "housemaid": return housemaid;
            case "entrepreneur": return entrepreneur;
            case "student": return student;
            case "blue-collar": return blueCollar;
            case "self-employed": return selfEmployed;
            case "retired": return retired;
            case "technician": return technician;
            case "services": return services;
            default: return null;
        }
    }
}
