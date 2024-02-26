package com.codersquare.registration.email;

import org.springframework.stereotype.Service;

public class EmailGenerator {

    public static String generateConfirmationEmail(String name, String activationLink) {
        return String.format("""
                <!DOCTYPE html>
                <html lang="en">
                
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Email Confirmation</title>
                    <style>
                        /* Inline CSS styles */
                        body {
                            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                            background-color: #f5f5f5;
                            margin: 0;
                            padding: 0;
                        }

                        .container {
                            width: 100%%;
                            max-width: 600px;
                            margin: 0 auto;
                            background-color: #ffffff;
                            border-radius: 10px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                            overflow: hidden;
                        }

                        header {
                            background-color: #212121;
                            color: #ffffff;
                            text-align: center;
                            padding: 20px 0;
                        }

                        header h1 {
                            margin: 0;
                            font-size: 28px;
                            font-weight: bold;
                        }

                        .content {
                            padding: 20px;
                            text-align: center;
                        }

                        .content p {
                            margin: 0 0 20px;
                            font-size: 18px;
                            line-height: 1.6;
                            color: #333333;
                        }

                        .cta-button {
                            display: inline-block;
                            padding: 15px 30px;
                            background-color: #4caf50;
                            color: #ffffff;
                            text-decoration: none;
                            border-radius: 5px;
                            font-size: 18px;
                            transition: background-color 0.3s;
                        }

                        .cta-button:hover {
                            background-color: #45a049;
                        }

                        footer {
                            background-color: #f2f2f2;
                            text-align: center;
                            padding: 10px 0;
                        }

                        footer p {
                            margin: 0;
                            font-size: 14px;
                            color: #777777;
                        }
                    </style>
                </head>
                
                <body>
                    <div class="container">
                        <header>
                            <h1>Confirm your email</h1>
                        </header>
                        <div class="content">
                            <p>Hi %s,</p>
                            <p>Thank you for registering. Please click on the below link to activate your account:</p>
                            <blockquote>
                                <p><a class="cta-button" href="%s">Activate Now</a></p>
                            </blockquote>
                            <p>Link will expire in 15 minutes. See you soon.</p>
                        </div>
                        <footer>
                            <p>&copy; 2022 Codersquare. All rights reserved.</p>
                        </footer>
                    </div>
                </body>
                
                </html>
                """, name, activationLink);
    }

        public static String generateConfirmationSuccessEmail(String name) {
            return String.format("""
                <!DOCTYPE html>
                <html lang="en">
                
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Email Confirmed</title>
                    <style>
                        /* Inline CSS styles */
                        body {
                            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                            background-color: #f5f5f5;
                            margin: 0;
                            padding: 0;
                        }

                        .container {
                            width: 80%%;
                            max-width: 800px;
                            margin: 0 auto;
                            background-color: #ffffff;
                            border-radius: 10px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                            overflow: hidden;
                            margin-top: 50px;
                        }

                        header {
                            background-color: #4caf50;
                            color: #ffffff;
                            text-align: center;
                            padding: 20px 0;
                        }

                        header h1 {
                            margin: 0;
                            font-size: 28px;
                            font-weight: bold;
                        }

                        .content {
                            padding: 20px;
                            text-align: center;
                        }

                        .content p {
                            margin: 0 0 20px;
                            font-size: 18px;
                            line-height: 1.6;
                            color: #333333;
                        }

                        .cta-button {
                            display: inline-block;
                            padding: 15px 30px;
                            background-color: #45a049;
                            color: #ffffff;
                            text-decoration: none;
                            border-radius: 5px;
                            font-size: 18px;
                            transition: background-color 0.3s;
                        }

                        .cta-button:hover {
                            background-color: #4caf50;
                        }

                        footer {
                            background-color: #f2f2f2;
                            text-align: center;
                            padding: 10px 0;
                        }

                        footer p {
                            margin: 0;
                            font-size: 14px;
                            color: #777777;
                        }
                    </style>
                </head>
                
                <body>
                    <div class="container">
                        <header>
                            <h1>Email Confirmed</h1>
                        </header>
                        <div class="content">
                            <p>Hi %s,</p>
                            <p>Your email has been successfully confirmed. Welcome to Codersquare!</p>
                            <p>Start exploring and learning with our community.</p>
                            <p><a class="cta-button" href="https://codersquare.com">Visit Codersquare</a></p>
                        </div>
                        <footer>
                            <p>&copy; 2022 Codersquare. All rights reserved.</p>
                        </footer>
                    </div>
                </body>
                
                </html>
                """, name);
        }
}
