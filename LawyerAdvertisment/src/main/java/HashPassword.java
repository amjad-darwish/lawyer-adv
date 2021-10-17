import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashPassword {
	public static void main(String[] args) {
//		System.out.println(new SimpleDateFormat("MM/dd/yy").format(new Date()));
//		System.out.println(PrintLettersContoller.firstLetterToUpperCase("test"));
		String plainPassword = "test";
		String hashedPassWord = new BCryptPasswordEncoder().encode(plainPassword);
		
//		System.out.println(new BCryptPasswordEncoder().encode(plainPassword));
//		System.out.println(new BCryptPasswordEncoder().encode(plainPassword));
		System.out.println(hashedPassWord);
	}
}
