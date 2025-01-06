CREATE TABLE `Member` (
                          `id`	int	NOT NULL,
                          `name`	varchar(16)	NOT NULL,
                          `email`	varchar(32)	NOT NULL,
                          `password`	varchar(32)	NOT NULL,
                          `created_at`	datetime	NOT NULL	DEFAULT NOW(),
                          `deleted_at`	datetime	NULL,
                          `role_id`	int	NOT NULL,
                          `alias`	varchar(16)	NULL,
                          `user_type`	boolean	NULL	DEFAULT TRUE,
                          `recent_datetime`	datetime	NULL,
                          `updated_at`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `GoogleOauthAccount` (
                                      `id`	int	NOT NULL,
                                      `account_id`	varchar(32)	NOT NULL,
                                      `access_token`	varchar(64)	NOT NULL,
                                      `refresh_token`	varchar(64)	NOT NULL,
                                      `created_at`	datetime	NOT NULL	DEFAULT NOW(),
                                      `deleted_at`	datetime	NOT NULL,
                                      `oauth_account_id`	int	NOT NULL
);

CREATE TABLE `UserOauthAccount` (
                                    `id`	int	NOT NULL,
                                    `type`	varchar(16)	NOT NULL,
                                    `created_at`	datetime	NOT NULL	DEFAULT NOW(),
                                    `deleted_at`	datetime	NULL,
                                    `user_id`	int	NOT NULL
);

CREATE TABLE `Role` (
                        `id`	int	NOT NULL,
                        `name`	varchar(16)	NULL
);

CREATE TABLE `Approver` (
                            `id`	int	NOT NULL,
                            `user_id`	int	NOT NULL,
                            `first_approver_id`	int	NOT NULL,
                            `sec_approver_id`	int	NOT NULL
);

CREATE TABLE `Holiday` (
                           `id`	int	NOT NULL,
                           `total_leave`	int	NULL,
                           `remained_leave`	int	NOT NULL	DEFAULT 0,
                           `used_leave`	int	NOT NULL	DEFAULT 0,
                           `used_official_leave`	int	NULL,
                           `created_at`	datetime	NOT NULL	DEFAULT NOW(),
                           `deleted_at`	datetime	NULL,
                           `updated_at`	TIMESTAMP	NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           `user_id`	int	NOT NULL
);

CREATE TABLE `HolidayRequest` (
                                  `id`	int	NOT NULL,
                                  `previous_remained_leave`	int	NOT NULL	DEFAULT 0,
                                  `after_remained_leave`	int	NOT NULL	DEFAULT 0,
                                  `type`	varchar(16)	NOT NULL,
                                  `unit`	varchar(16)	NOT NULL,
                                  `start_date`	date	NULL,
                                  `end_date`	date	NULL,
                                  `start_time`	time	NULL,
                                  `end_time`	time	NULL,
                                  `request_leave`	int	NOT NULL,
                                  `note`	varchar(64)	NULL,
                                  `status`	varchar(8)	NULL,
                                  `created_at`	datetime	NULL	DEFAULT NOW(),
                                  `deleted_at`	datetime	NULL,
                                  `user_id`	int	NOT NULL,
                                  `approver_id`	int	NOT NULL,
                                  `receiver_id`	int	NOT NULL
);

CREATE TABLE `HolidayRequestApproval` (
                                          `id`	int	NOT NULL,
                                          `first_approved`	boolean	NOT NULL	DEFAULT FALSE,
                                          `sec_approved`	boolean	NOT NULL	DEFAULT FALSE,
                                          `first_approved_at`	datetime	NULL,
                                          `sec_approved_at`	datetime	NULL,
                                          `created_at`	datetime	NOT NULL	DEFAULT NOW(),
                                          `deleted_at`	datetime	NULL,
                                          `request_id`	int	NOT NULL
);

CREATE TABLE `Notification` (
                                `id`	int	NOT NULL,
                                `message`	varchar(64)	NULL,
                                `watched`	boolean	NOT NULL	DEFAULT FALSE,
                                `created_at`	datetime	NOT NULL	DEFAULT NOW(),
                                `deleted_at`	datetime	NULL,
                                `event_id`	int	NOT NULL,
                                `sender_id`	int	NOT NULL,
                                `receiver_id`	int	NOT NULL
);

CREATE TABLE `Event` (
                         `event_id`	int	NOT NULL,
                         `event_type`	varchar(16)	NOT NULL,
                         `entity_type`	varchar(16)	NULL
);

CREATE TABLE `HolidayRequestDisplay` (
                                         `id`	int	NOT NULL,
                                         `created_at`	datetime	NOT NULL	DEFAULT NOW(),
                                         `deleted_at`	datetime	NULL,
                                         `request_id`	int	NOT NULL,
                                         `user_id`	int	NOT NULL
);

ALTER TABLE `Member` ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (
                                                             `id`
    );

ALTER TABLE `GoogleOauthAccount` ADD CONSTRAINT `PK_GOOGLEOAUTHACCOUNT` PRIMARY KEY (
                                                                                     `id`
    );

ALTER TABLE `UserOauthAccount` ADD CONSTRAINT `PK_USEROAUTHACCOUNT` PRIMARY KEY (
                                                                                 `id`
    );

ALTER TABLE `Role` ADD CONSTRAINT `PK_ROLE` PRIMARY KEY (
                                                         `id`
    );

ALTER TABLE `Approver` ADD CONSTRAINT `PK_APPROVER` PRIMARY KEY (
                                                                 `id`
    );

ALTER TABLE `Holiday` ADD CONSTRAINT `PK_HOLIDAY` PRIMARY KEY (
                                                               `id`
    );

ALTER TABLE `HolidayRequest` ADD CONSTRAINT `PK_HOLIDAYREQUEST` PRIMARY KEY (
                                                                             `id`
    );

ALTER TABLE `HolidayRequestApproval` ADD CONSTRAINT `PK_HOLIDAYREQUESTAPPROVAL` PRIMARY KEY (
                                                                                             `id`
    );

ALTER TABLE `Notification` ADD CONSTRAINT `PK_NOTIFICATION` PRIMARY KEY (
                                                                         `id`
    );

ALTER TABLE `Event` ADD CONSTRAINT `PK_EVENT` PRIMARY KEY (
                                                           `event_id`
    );

ALTER TABLE `HolidayRequestDisplay` ADD CONSTRAINT `PK_HOLIDAYREQUESTDISPLAY` PRIMARY KEY (
                                                                                           `id`
    );

ALTER TABLE `Member` ADD CONSTRAINT `FK_Role_TO_Member_1` FOREIGN KEY (
                                                                       `role_id`
    )
    REFERENCES `Role` (
                       `id`
        );

ALTER TABLE `GoogleOauthAccount` ADD CONSTRAINT `FK_UserOauthAccount_TO_GoogleOauthAccount_1` FOREIGN KEY (
                                                                                                           `oauth_account_id`
    )
    REFERENCES `UserOauthAccount` (
                                   `id`
        );

ALTER TABLE `UserOauthAccount` ADD CONSTRAINT `FK_Member_TO_UserOauthAccount_1` FOREIGN KEY (
                                                                                             `user_id`
    )
    REFERENCES `Member` (
                         `id`
        );

ALTER TABLE `Approver` ADD CONSTRAINT `FK_Member_TO_Approver_1` FOREIGN KEY (
                                                                             `user_id`
    )
    REFERENCES `Member` (
                         `id`
        );

ALTER TABLE `Approver` ADD CONSTRAINT `FK_Member_TO_Approver_2` FOREIGN KEY (
                                                                             `first_approver_id`
    )
    REFERENCES `Member` (
                         `id`
        );

ALTER TABLE `Approver` ADD CONSTRAINT `FK_Member_TO_Approver_3` FOREIGN KEY (
                                                                             `sec_approver_id`
    )
    REFERENCES `Member` (
                         `id`
        );

ALTER TABLE `Holiday` ADD CONSTRAINT `FK_Member_TO_Holiday_1` FOREIGN KEY (
                                                                           `user_id`
    )
    REFERENCES `Member` (
                         `id`
        );

ALTER TABLE `HolidayRequest` ADD CONSTRAINT `FK_Member_TO_HolidayRequest_1` FOREIGN KEY (
                                                                                         `user_id`
    )
    REFERENCES `Member` (
                         `id`
        );

ALTER TABLE `HolidayRequest` ADD CONSTRAINT `FK_Member_TO_HolidayRequest_2` FOREIGN KEY (
                                                                                         `receiver_id`
    )
    REFERENCES `Member` (
                         `id`
        );

ALTER TABLE `HolidayRequest` ADD CONSTRAINT `FK_Approver_TO_HolidayRequest_1` FOREIGN KEY (
                                                                                           `approver_id`
    )
    REFERENCES `Approver` (
                           `id`
        );

ALTER TABLE `HolidayRequestApproval` ADD CONSTRAINT `FK_HolidayRequest_TO_HolidayRequestApproval_1` FOREIGN KEY (
                                                                                                                 `request_id`
    )
    REFERENCES `HolidayRequest` (
                                 `id`
        );

ALTER TABLE `Notification` ADD CONSTRAINT `FK_Event_TO_Notification_1` FOREIGN KEY (
                                                                                    `event_id`
    )
    REFERENCES `Event` (
                        `event_id`
        );

ALTER TABLE `Notification` ADD CONSTRAINT `FK_Member_TO_Notification_1` FOREIGN KEY (
                                                                                     `sender_id`
    )
    REFERENCES `Member` (
                         `id`
        );

ALTER TABLE `Notification` ADD CONSTRAINT `FK_Member_TO_Notification_2` FOREIGN KEY (
                                                                                     `receiver_id`
    )
    REFERENCES `Member` (
                         `id`
        );

ALTER TABLE `HolidayRequestDisplay` ADD CONSTRAINT `FK_HolidayRequest_TO_HolidayRequestDisplay_1` FOREIGN KEY (
                                                                                                               `request_id`
    )
    REFERENCES `HolidayRequest` (
                                 `id`
        );

ALTER TABLE `HolidayRequestDisplay` ADD CONSTRAINT `FK_Member_TO_HolidayRequestDisplay_1` FOREIGN KEY (
                                                                                                       `user_id`
    )
    REFERENCES `Member` (
                         `id`
        );



INSERT INTO `Event` (`event_id`, `event_type`, `entity_type`) VALUES
                                                                  (1, '휴가 신청', 'HolidayRequest'),
                                                                  (2, '휴가 승인', 'HolidayRequest'),
                                                                  (3, '휴가 반려', 'HolidayRequest'),
                                                                  (4, '휴가 부여', 'Holiday'),
                                                                  (5, '휴가 차감', 'Holiday'),
                                                                  (6, '인수자 지정', 'HolidayRequest'),
                                                                  (7, '그룹 방출', 'Member'),
                                                                  (8, '그룹 가입', 'Member');
