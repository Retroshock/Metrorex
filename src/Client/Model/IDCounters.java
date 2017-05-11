package Client.Model;

import Client.Exceptions.OutOfIDsException;

/**
 * Created by Adrian on 15.04.2017.
 */
public class IDCounters {
    public static final long MONTH_MAX = 250000000;
    public static final long DAY_MAX = 500000000;
    public static final long TEN_MAX = 750000000;

    public static long monthId = 0;
    public static long dayId = 250000001;
    public static long tenId = 500000001;
    public static long twoId = 750000001;

    public static long getID(Type tip) throws OutOfIDsException {
        switch (tip) {
            case Day:{
                if (dayId < DAY_MAX)
                    dayId++;
                else
                    throw new OutOfIDsException();
                return dayId;
            }
            case Month:{
                if (monthId < MONTH_MAX)
                    monthId++;
                else
                    throw new OutOfIDsException();
                return monthId;

            }
            case Ten:{
                if (tenId < TEN_MAX)
                    tenId++;
                else
                    throw new OutOfIDsException();
                return tenId;

            }
            case Two:{
                if (twoId < Long.MAX_VALUE -1)
                    twoId++;
                else
                    throw new OutOfIDsException();
                return twoId;
            }

        }
        return -1;
    }
}
