package cart.business.domain.member;

public class MemberPassword {

    private static final int MIN_SIZE = 4;
    private static final int MAX_SIZE = 10;

    private final String password;

    public MemberPassword(String password) {
        validate(password);
        this.password = password;
    }

    private void validate(String password) {
        if (password.length() > MAX_SIZE || password.length() < MIN_SIZE) {
            throw new IllegalArgumentException(String.format("비밀번호는 %d자 이상, %d자 이하여야 합니다.", MIN_SIZE, MAX_SIZE));
        }
    }

    public String getValue() {
        return password;
    }
}
