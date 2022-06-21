package SWT2.Security;
import java.util.HashSet;
import java.util.Set;


    public enum Role {
        USER(0x01), // 0 User
        ADMIN(0x01 << 2);

        private final int mask;

        Role(int mask) {
            this.mask = mask;
        }

        public static Set<Role> createFromMask(long mask) {
            Set<Role> output = new HashSet<>();
            for (Role r : values()) {
                if ((r.mask & mask) != 0) {
                    output.add(r);
                }
            }
            return output;
        }
    }

