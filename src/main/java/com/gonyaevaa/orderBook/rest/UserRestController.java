package com.gonyaevaa.orderBook.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.gonyaevaa.orderBook.mapper.UserMapper;
import com.gonyaevaa.orderBook.model.User;
import com.gonyaevaa.orderBook.model.Views;
import com.gonyaevaa.orderBook.model.dto.UserDto;
import com.gonyaevaa.orderBook.model.resp.UserResponseWithIdAndOrders;
import com.gonyaevaa.orderBook.model.resp.UserResponseWithoutOrders;
import com.gonyaevaa.orderBook.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "user", value = "Operations pertaining to users")
public class UserRestController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public UserRestController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @ApiOperation(value = "View a list of available users", response = UserResponseWithoutOrders.class)
    @JsonView(Views.PartInfo.class)
    @GetMapping(value = "")
    public List<UserDto> listAllUsers() {
        log.info("Show all users");

        return this.userMapper.
                toUserDtos(this.userService.findAllUsers());
    }

    @ApiOperation(value = "Find user by ID", notes = "Returns one user with all his orders",
            response = UserResponseWithIdAndOrders.class)
    @GetMapping("{userId}")
    public UserDto getUser(@ApiParam(value = "Id of the user to be obtained", required = true)
                           @PathVariable("userId") Long userId) {
        log.info("Fetch user with id {}", userId);

        User user = this.userService.findUserById(userId);

        return this.userMapper.toUserDto(user);
    }

    @ApiOperation(value = "Create a new user", response = UserResponseWithoutOrders.class)
    @JsonView(Views.PartInfo.class)
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@ApiParam(value = "User object that needs to be added", name = "User", required = true)
                              @Valid @RequestBody UserDto userDto) {
        log.info("Create user : {}", userDto);

        User newUser = this.userMapper.toUser(userDto);
        this.userService.saveUser(newUser);

        return this.userMapper.toUserDto(newUser);

    }

    @ApiOperation(value = "Update an existing user", response = UserResponseWithoutOrders.class)
    @JsonView(Views.PartInfo.class)
    @PutMapping("{userId}")
    public UserDto updateUser(@ApiParam(value = "ID of user that needs to be updated", required = true)
                              @PathVariable("userId") Long userId,
                              @ApiParam(value = "Updated user object", name = "User", required = true)
                              @Valid @RequestBody UserDto userDto) {
        log.info("Update user with id {}", userId);

        User currentUser = this.userService.findUserById(userId);

        this.userMapper.userDtoIntoUser(userDto, currentUser);
        this.userService.saveUser(currentUser);

        return this.userMapper.toUserDto(currentUser);
    }

    @ApiOperation(value = "Delete an user")
    @DeleteMapping("{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@ApiParam(value = "User id to delete", required = true)
                           @PathVariable("userId") Long userId) {
        log.info("Delete user with id {}", userId);

        this.userService.findUserById(userId);
        this.userService.deleteUser(userId);
    }
}
