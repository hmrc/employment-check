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

package uk.gov.hmrc.employmentcheck

import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.mvc.Results.Status
import uk.gov.hmrc.api.controllers.ErrorResponse

package object controllers {

  case object ErrorNinoNotVisible extends ErrorResponse(404, "EPAYE_NINO_UNKNOWN", "The provided NINO was not recognised")

  implicit class ErrorResponseSyntax(er: ErrorResponse) {
    def toResult: Result = Status(er.httpStatusCode)(Json.toJson(er))
  }

}
