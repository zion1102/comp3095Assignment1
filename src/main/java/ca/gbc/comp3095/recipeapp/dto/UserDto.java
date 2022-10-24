package ca.gbc.comp3095.recipeapp.dto;
/**********************************************************************************
 * Project: < My Recipe App >
 * Assignment: < Assignment #1 >
 * Author(s): < Zion Henry, Mohsen Yahya>
 * Student Number: < 101232420, 101241827 >
 * Description: <User Dto Structure>
 *********************************************************************************/
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;



}
