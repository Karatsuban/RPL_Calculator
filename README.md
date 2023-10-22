# RPL_Calculator
A RPL calculator CLI implementing basic features



## Usage


### Compilation

You can compile the code with the command `$ make`.


### Execute

Launch the program with the command:  
`$ java CalcServer size <value> [user|users] [remote|local] [shared|not_share] [log|replay] <file>`

- size <value> : the created RPL will be created with this size (OPTIONAL)
- user : specify the there will be only one user
- users : multiple connection can be made


Options for single users
- remote : the program can be accessed via socket
- local : the program is directly accessible from the command line that launched it
- log <file> : all of the user's commands will be stored in the file
- replay <file> : the file is used as input of the calculator

Options for multiple users

- remote : the program will be available via socket (OPTIONAL)
- shared : a unique RPL Calculator is created, shared by every user
- not-shared : each user gets its own RPL Calculator

> If not arguments are provided, the program will start for one user in local mode  
> The replay/log options are not available for multiple users  
> There is no local options for multiple users


