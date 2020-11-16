package com.cauh.esop.domain;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Immutable
@Subselect("select a.user_id, max(b.id) training_period_id from(\n" +
        "\tselect b.id user_id, b.indate, a.document_version_id from(\n" +
        "\t\tselect distinct jd.username, m.document_version_id from(\n" +
        "\t\t\tselect a.username, b.jd_id  \n" +
        "\t\t\tfrom c_user_job_description a inner join c_job_description_version b\n" +
        "\t\t\ton a.job_description_version_id = b.id\n" +
        "\t\t\twhere a.status = 'APPROVED'\n" +
        "\t\t) jd inner join s_sop_training_matrix m\n" +
        "\t\ton (jd.jd_id = m.job_description_id or m.training_all = 1)\n" +
        "\t) a inner join account b\n" +
        "\ton a.username = b.username\n" +
        "\twhere b.enabled = 1\n" +
        "\tand b.indate is not null\n" +
        "\tand b.training = 1\n" +
        ") a inner join s_training_period b\n" +
        "on (a.document_version_id = b.document_version_id\n" +
        "\tand (\n" +
        "\t\t(b.type='SELF' and b.start_date<=GETDATE())\n" +
        "\t\tor (b.type='RE_FRESH'  and b.start_date>=a.indate)\n" +
        "\t\tor (b.type='RE_TRAINING' and b.retraining_user_id=a.user_id)\n" +
        "\t)\n" +
        ") inner join s_document_version d\n" +
        "on a.document_version_id = d.id\n" +
        "and d.status in('APPROVED', 'EFFECTIVE')\n" +
        "group by a.user_id, d.document_id, d.status")
//@Subselect("select a.user_id, a.document_version_id, b.id training_period_id from(\n" +
//        "\tselect b.id user_id, b.indate, a.document_version_id from(\n" +
//        "\t\tselect distinct jd.username, m.document_version_id from(\n" +
//        "\t\t\tselect a.username, b.jd_id  \n" +
//        "\t\t\tfrom c_user_job_description a inner join c_job_description_version b\n" +
//        "\t\t\ton a.job_description_version_id = b.id\n" +
//        "\t\t\twhere a.status = 'APPROVED'\n" +
//        "\t\t) jd inner join s_sop_training_matrix m\n" +
//        "\t\ton (jd.jd_id = m.job_description_id or m.training_all = 1)\n" +
//        "\t) a inner join c_users b\n" +
//        "\ton a.username = b.username\n" +
//        "\twhere b.enabled = 1\n" +
//        "\tand b.indate is not null\n" +
//        "\tand b.training = 1\n" +
//        ") a inner join s_training_period b\n" +
//        "on (a.document_version_id = b.document_version_id\n" +
//        "\tand (\n" +
//        "\t\t(b.type='SELF' and b.start_date<=GETDATE())\n" +
//        "\t\tor (b.type='RE_FRESH'  and b.start_date>=a.indate)\n" +
//        "\t\tor (b.type='RE_TRAINING' and b.retraining_user_id=a.user_id)\n" +
//        "\t)\n" +
//        ") ")
@IdClass(MatrixId.class)
public class UserMatrix {
    @Id
    private Integer userId;

//    @Id
//    private String documentVersionId;

    @Id
    private Integer trainingPeriodId;
}
