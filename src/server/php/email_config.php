<?php
if (!defined('ACCESS_ALLOWED')) {
    die("Restricted Access");
}

/*
|--------------------------------------------------------------------------
| Email Configuration for PHPMailer
|--------------------------------------------------------------------------
| This configuration file contains the SMTP credentials used to send emails.
| 
|
| ⚠️ IMPORTANT:
| - Replace the email and app password below with your own.
| - You MUST enable 2-step verification on your Gmail account and create 
|   an "App Password" for this to work (regular Gmail passwords won't work).
| - Do NOT share or commit this file to public repositories.
|
| Example setup: https://support.google.com/accounts/answer/185833
*/

return [
    // For Mailer Config
    'username' => '',    // Your email here
    'password' => '',     // Your App Password here


    // For Owner Login Config
    // Note: If these fields are left empty, the system will use the following defaults:
    // Email -> russel@gmail.com
    // Password -> 123
    // Pin   -> 03202005

    'owner_email' => 'russel@gmail.com', // Custom email for owner login (can be any valid email)
    'owner_password' => '123',           // Custom Password for owner login
    'owner_pin'   => '03202005'          // Custom PIN for owner login
];
