/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.employmentcheck.config

import org.joda.time.LocalDate
import play.api.mvc.QueryStringBindable
import uk.gov.hmrc.time.DateConverter

import scala.util.Try

object QueryBinders {

  implicit def bindableLocalDate(implicit stringBinder: QueryStringBindable[String]) = new QueryStringBindable[LocalDate] {

    def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, LocalDate]] = {
      params.get(key).flatMap(_.headOption).map { date: String => (Try {
        Right(DateConverter.parseToLocalDate(date))
      } recover {
        case e: Exception => Left("date parameter is in the wrong format. Should be (yyyy-MM-dd)")
      }).get
      }
    }

    def unbind(key: String, value: LocalDate): String = QueryStringBindable.bindableString.unbind(key, DateConverter.formatToString(value))
  }
}
