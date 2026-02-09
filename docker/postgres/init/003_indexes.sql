create unique index uniq_index_username_email
    on users_for_test ((lower(user_name)), email);
